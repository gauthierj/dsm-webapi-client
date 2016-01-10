package com.github.gauthierj.dsm.webapi.client.filestation.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gauthierj.dsm.webapi.client.DsmWebapiResponse;
import com.github.gauthierj.dsm.webapi.client.filestation.common.File;
import com.github.gauthierj.dsm.webapi.client.filestation.common.PaginationAndSorting;
import com.google.common.base.Joiner;
import com.jayway.awaitility.core.ConditionTimeoutException;
import com.github.gauthierj.dsm.webapi.client.AbstractDsmServiceImpl;
import com.github.gauthierj.dsm.webapi.client.DsmWebApiResponseError;
import com.github.gauthierj.dsm.webapi.client.DsmWebapiRequest;
import com.github.gauthierj.dsm.webapi.client.filestation.common.FileType;
import com.github.gauthierj.dsm.webapi.client.filestation.common.TaskId;
import com.github.gauthierj.dsm.webapi.client.filestation.exception.TaskTimeOutException;
import com.github.gauthierj.dsm.webapi.client.timezone.TimeZoneUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static com.jayway.awaitility.Awaitility.await;

@Component
public class SearchServiceImpl extends AbstractDsmServiceImpl implements SearchService {

    // API Infos
    private static final String API_ID = "SYNO.FileStation.Search";
    private static final String API_VERSION = "1";

    // API Methods
    private static final String METHOD_CLEAN = "clean";
    private static final String METHOD_START = "start";
    private static final String METHOD_STOP = "stop";

    // Parameters
    private static final String PARAMETER_ADDITIONAL = "additional";
    private static final String PARAMETER_ATIME_FROM = "atime_from";
    private static final String PARAMETER_ATIME_TO = "atime_to";
    private static final String PARAMETER_CRTIME_FROM = "crtime_from";
    private static final String PARAMETER_CRTIME_TO = "crtime_to";
    private static final String PARAMETER_EXTENSION = "extension";
    private static final String PARAMETER_FILETYPE = "filetype";
    private static final String PARAMETER_FOLDER_PATH = "folder_path";
    private static final String PARAMETER_GROUP = "group";
    private static final String PARAMETER_LIMIT = "limit";
    private static final String PARAMETER_MTIME_FROM = "mtime_from";
    private static final String PARAMETER_MTIME_TO = "mtime_to";
    private static final String PARAMETER_OFFSET = "offset";
    private static final String PARAMETER_OWNER = "owner";
    private static final String PARAMETER_PATTERN = "pattern";
    private static final String PARAMETER_RECURSIVE = "recursive";
    private static final String PARAMETER_SIZE_FROM = "size_from";
    private static final String PARAMETER_SIZE_TO = "size_to";
    private static final String PARAMETER_SORT_DIRECTION = "sort_direction";
    private static final String PARAMETER_SORT_BY = "sort_by";
    private static final String PARAMETER_TASKID = "taskid";

    // Parameters values
    private static final String PARAMETER_VALUE_ADDITIONAL = "real_path,size,owner,time,perm,type";

    @Autowired
    private TimeZoneUtil timeZoneUtil;

    @Value("${dsm.webapi.synchronousSearch.timeout:30}")
    private int synchronousSearchTimeout;

    private Function<LocalDateTime, String> localDateToTimeStringFunction;

    public SearchServiceImpl() {
        super(API_ID);
    }

    @PostConstruct
    public void initSearchService() {
        localDateToTimeStringFunction = localDateTime -> Long.toString(localDateTime.toEpochSecond(timeZoneUtil.getZoneOffset()));
    }

    @Override
    public String start(String searchedFolderPath, boolean recursive, SearchCriteria searchCriteria) {
        DsmWebapiRequest request = new DsmWebapiRequest(getApiId(), API_VERSION, getApiInfo().getPath(), METHOD_START)
                .parameter(PARAMETER_FOLDER_PATH, searchedFolderPath)
                .parameter(PARAMETER_RECURSIVE, Boolean.toString(recursive))
                .optionalStringParameter(PARAMETER_PATTERN, Joiner.on(',').join(searchCriteria.getPatterns()))
                .optionalStringParameter(PARAMETER_EXTENSION, Joiner.on(',').join(searchCriteria.getExtensions()))
                .optionalParameter(PARAMETER_FILETYPE, searchCriteria.getFileType(), FileType::getRepresentation)
                .optionalParameter(PARAMETER_SIZE_FROM, searchCriteria.getSizeFrom())
                .optionalParameter(PARAMETER_SIZE_TO, searchCriteria.getSizeTo())
                .optionalParameter(PARAMETER_MTIME_FROM, searchCriteria.getLastModifiedTimeFrom(), localDateToTimeStringFunction)
                .optionalParameter(PARAMETER_MTIME_TO, searchCriteria.getLastModifiedTimeTo(), localDateToTimeStringFunction)
                .optionalParameter(PARAMETER_CRTIME_FROM, searchCriteria.getCreationTimeFrom(), localDateToTimeStringFunction)
                .optionalParameter(PARAMETER_CRTIME_TO, searchCriteria.getCreationTimeTo(), localDateToTimeStringFunction)
                .optionalParameter(PARAMETER_ATIME_FROM, searchCriteria.getLastAccessTimeFrom(), localDateToTimeStringFunction)
                .optionalParameter(PARAMETER_ATIME_TO, searchCriteria.getLastAccessTimeTo(), localDateToTimeStringFunction)
                .optionalParameter(PARAMETER_OWNER, searchCriteria.getOwnerUserName())
                .optionalParameter(PARAMETER_GROUP, searchCriteria.getGroupName());
        StartSearchResponse call = getDsmWebapiClient().call(request, StartSearchResponse.class);
        return call.getData().getTaskId();
    }

    @Override
    public boolean isFinished(String taskId) {
        return getResult(taskId, new PaginationAndSorting(0, 1, PaginationAndSorting.Sort.NAME, PaginationAndSorting.SortDirection.ASC)).isFinished();
    }

    @Override
    public SearchResult getResult(String taskId, PaginationAndSorting paginationAndSorting) {
        DsmWebapiRequest request = new DsmWebapiRequest(getApiId(), "1", getApiInfo().getPath(), "list")
                .parameter(PARAMETER_TASKID, taskId)
                .parameter(PARAMETER_OFFSET, paginationAndSorting.getOffset())
                .parameter(PARAMETER_LIMIT, paginationAndSorting.getLimit())
                .parameter(PARAMETER_SORT_BY, paginationAndSorting.getSortBy().getRepresentation())
                .parameter(PARAMETER_SORT_DIRECTION, paginationAndSorting.getSortDirection().getRepresentation())
                .parameter(PARAMETER_ADDITIONAL, PARAMETER_VALUE_ADDITIONAL)
                ;
        SearchResultResponse call = getDsmWebapiClient().call(request, SearchResultResponse.class);
        return call.getData();
    }

    @Override
    public List<File> getResult(String taskId) {
        return getResult(taskId, PaginationAndSorting.DEFAULT_PAGINATION_AND_SORTING).getElements();
    }

    @Override
    public void stop(List<String> taskIds) {
        DsmWebapiRequest request = new DsmWebapiRequest(getApiId(), API_VERSION, getApiInfo().getPath(), METHOD_STOP)
                .parameter(PARAMETER_TASKID, Joiner.on(',').join(taskIds));
        DsmWebapiResponse response = getDsmWebapiClient().call(request, DsmWebapiResponse.class);
        // TODO handle failure
    }

    @Override
    public void stop(String taskId) {
        stop(Collections.singletonList(taskId));
    }

    @Override
    public void clean(List<String> taskIds) {
        DsmWebapiRequest request = new DsmWebapiRequest(getApiId(),  API_VERSION, getApiInfo().getPath(), METHOD_CLEAN)
                .parameter(PARAMETER_TASKID, Joiner.on(',').join(taskIds));
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
            throw new TaskTimeOutException(e);
        } finally {
            clean(taskId);
        }
    }

    public static class StartSearchResponse extends DsmWebapiResponse<TaskId> {

        public StartSearchResponse(@JsonProperty("success") boolean success, @JsonProperty("data") TaskId data, @JsonProperty("error") DsmWebApiResponseError error) {
            super(success, data, error);
        }
    }

    public static class SearchResultResponse extends DsmWebapiResponse<SearchResult> {

        public SearchResultResponse(@JsonProperty("success") boolean success, @JsonProperty("data") SearchResult data, @JsonProperty("error") DsmWebApiResponseError error) {
            super(success, data, error);
        }
    }
}
