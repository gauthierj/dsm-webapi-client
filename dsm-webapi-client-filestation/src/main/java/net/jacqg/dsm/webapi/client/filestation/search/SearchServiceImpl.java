package net.jacqg.dsm.webapi.client.filestation.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Joiner;
import com.jayway.awaitility.core.ConditionTimeoutException;
import net.jacqg.dsm.webapi.client.AbstractDsmServiceImpl;
import net.jacqg.dsm.webapi.client.DsmWebapiRequest;
import net.jacqg.dsm.webapi.client.DsmWebapiResponse;
import net.jacqg.dsm.webapi.client.filestation.common.FileType;
import net.jacqg.dsm.webapi.client.filestation.common.PaginationAndSorting;
import net.jacqg.dsm.webapi.client.filestation.exception.SearchTimeOutException;
import net.jacqg.dsm.webapi.client.filestation.filelist.File;
import net.jacqg.dsm.webapi.client.timezone.TimeZoneUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static com.jayway.awaitility.Awaitility.await;

@Component
public class SearchServiceImpl extends AbstractDsmServiceImpl implements SearchService {

    @Autowired
    private TimeZoneUtil timeZoneUtil;

    @Value("${dsm.webapi.synchronousSearch.timeout:30}")
    private int synchronousSearchTimeout;

    private Function<LocalDateTime, String> localDateToTimeStringFunction;

    public SearchServiceImpl() {
        super("SYNO.FileStation.Search");
    }

    @PostConstruct
    public void initSearchService() {
        localDateToTimeStringFunction = localDateTime -> Long.toString(localDateTime.toEpochSecond(timeZoneUtil.getZoneOffset()));
    }

    @Override
    public String start(String searchedFolderPath, boolean recursive, SearchCriteria searchCriteria) {
        DsmWebapiRequest request = null;
        try {
            request = new DsmWebapiRequest(getApiId(), "1", getApiInfo().getPath(), "start")
                    .parameter("folder_path", URLEncoder.encode(searchedFolderPath, "UTF-8"))
                    .parameter("recursive", Boolean.toString(recursive))
                    .optionalStringParameter("pattern", Joiner.on(',').join(searchCriteria.getPatterns()))
                    .optionalStringParameter("extension", Joiner.on(',').join(searchCriteria.getExtensions()))
                    .optionalParameter("filetype", searchCriteria.getFileType(), FileType::getRepresentation)
                    .optionalParameter("size_from", searchCriteria.getSizeFrom())
                    .optionalParameter("size_to", searchCriteria.getSizeTo())
                    .optionalParameter("mtime_from", searchCriteria.getLastModifiedTimeFrom(), localDateToTimeStringFunction)
                    .optionalParameter("mtime_to", searchCriteria.getLastModifiedTimeTo(), localDateToTimeStringFunction)
                    .optionalParameter("crtime_from", searchCriteria.getCreationTimeFrom(), localDateToTimeStringFunction)
                    .optionalParameter("crtime_to", searchCriteria.getCreationTimeTo(), localDateToTimeStringFunction)
                    .optionalParameter("atime_from", searchCriteria.getLastAccessTimeFrom(), localDateToTimeStringFunction)
                    .optionalParameter("atime_to", searchCriteria.getLastAccessTimeTo(), localDateToTimeStringFunction)
                    .optionalParameter("owner", searchCriteria.getOwnerUserName())
                    .optionalParameter("group", searchCriteria.getGroupName());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StartSearchResponse call = getDsmWebapiClient().call(request, StartSearchResponse.class);
        return call.getData().getTaskId();
    }

    @Override
    public boolean isFinished(String taskId) {
        return getResult(taskId, new PaginationAndSorting(0, 0, PaginationAndSorting.Sort.NAME, PaginationAndSorting.SortDirection.ASC)).isFinished();
    }

    @Override
    public SearchResult getResult(String taskId, PaginationAndSorting paginationAndSorting) {
        DsmWebapiRequest request = new DsmWebapiRequest(getApiId(), "1", getApiInfo().getPath(), "list")
                .parameter("taskid", taskId)
                .parameter("offset", paginationAndSorting.getOffset())
                .parameter("limit", paginationAndSorting.getLimit())
                .parameter("sort_by", paginationAndSorting.getSortBy().getRepresentation())
                .parameter("sort_direction", paginationAndSorting.getSortDirection().getRepresentation());
        SearchResultResponse call = getDsmWebapiClient().call(request, SearchResultResponse.class);
        return call.getData();
    }

    @Override
    public List<File> getResult(String taskId) {
        return getResult(taskId, PaginationAndSorting.DEFAULT_PAGINATION_AND_SORTING).getElements();
    }

    @Override
    public void stop(List<String> taskIds) {
        DsmWebapiRequest request = new DsmWebapiRequest(getApiId(), "1", getApiInfo().getPath(), "stop")
                .parameter("taskid", Joiner.on(',').join(taskIds));
        DsmWebapiResponse response = getDsmWebapiClient().call(request, DsmWebapiResponse.class);
        // TODO handle failure
    }

    @Override
    public void stop(String taskId) {
        stop(Collections.singletonList(taskId));
    }

    @Override
    public void clean(List<String> taskIds) {
        DsmWebapiRequest request = new DsmWebapiRequest(getApiId(), "1", getApiInfo().getPath(), "clean")
                .parameter("taskid", Joiner.on(',').join(taskIds));
        DsmWebapiResponse response = getDsmWebapiClient().call(request, DsmWebapiResponse.class);
        // TODO handle failure
    }

    @Override
    public void clean(String taskId) {
        clean(Collections.singletonList(taskId));
    }

    @Override
    public List<File> synchronousSearch(String searchedFolderPath, boolean recursive, SearchCriteria searchCriteria) {
        String taskId = start(searchedFolderPath, recursive, searchCriteria);
        try {
            await().atMost(synchronousSearchTimeout, TimeUnit.SECONDS).until(() -> {
                return SearchServiceImpl.this.isFinished(taskId);
            });
            return getResult(taskId);
        } catch (ConditionTimeoutException e) {
            stop(taskId);
            throw new SearchTimeOutException(e);
        } finally {
            clean(taskId);
        }
    }

    public static class TaskId {

        private final String taskId;

        @JsonCreator
        public TaskId(@JsonProperty("taskid") String taskId) {
            this.taskId = taskId;
        }

        public String getTaskId() {
            return taskId;
        }
    }

    public static class SearchResult extends File.FileList {

        private final boolean finished;

        public SearchResult(@JsonProperty("finished") boolean finished, @JsonProperty("total") int total, @JsonProperty("offset") int offset, @JsonProperty("files") List<File> files) {
            super(total, offset, files);
            this.finished = finished;
        }

        public boolean isFinished() {
            return finished;
        }
    }

    public static class StartSearchResponse extends DsmWebapiResponse<TaskId> {

        public StartSearchResponse(@JsonProperty("success") boolean success, @JsonProperty("data") TaskId data, @JsonProperty("error") Error error) {
            super(success, data, error);
        }
    }

    public static class SearchResultResponse extends DsmWebapiResponse<SearchResult> {

        public SearchResultResponse(@JsonProperty("success") boolean success, @JsonProperty("data") SearchResult data, @JsonProperty("error") Error error) {
            super(success, data, error);
        }
    }
}
