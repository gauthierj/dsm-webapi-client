package net.jacqg.dsm.webapi.client.filestation.filelist;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Joiner;
import net.jacqg.dsm.webapi.client.AbstractDsmServiceImpl;
import net.jacqg.dsm.webapi.client.DsmWebapiRequest;
import net.jacqg.dsm.webapi.client.DsmWebapiResponse;
import net.jacqg.dsm.webapi.client.filestation.common.PaginationAndSorting;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class FileListServiceImpl extends AbstractDsmServiceImpl implements FileListService {

    public FileListServiceImpl() {
        super("SYNO.FileStation.List");
    }

    @Override
    public File.FileList list(PaginationAndSorting paginationAndSorting, String folderPath, Optional<List<String>> patterns, Optional<FileType> fileType, Optional<String> gotoPath) {
        FileListRespone response = getDsmWebapiClient().call(
                new DsmWebapiRequest(getApiId(), "1", getApiInfo().getPath(), "list")
                        .parameter("folder_path", folderPath)
                        .parameter("offset", Integer.toString(paginationAndSorting.getOffset()))
                        .parameter("limit", Integer.toString(paginationAndSorting.getLimit()))
                        .parameter("sort_by", paginationAndSorting.getSortBy().getRepresentation())
                        .parameter("sort_direction", paginationAndSorting.getSortDirection().getRepresentation())
                        .parameter("pattern", Joiner.on(',').join(patterns.orElse(Collections.<String>emptyList())))
                        .parameter("filetype", fileType.orElse(FileType.ALL).getRepresentation())
                        .parameter("goto_path", gotoPath.orElse(""))
                        .parameter("additional", "real_path,size,owner,time,perm,type,mount_point_type")
                , FileListRespone.class);
        // TODO handle errors ???
        return response.getData();
    }

    @Override
    public List<File> list(String folderPath, Optional<List<String>> patterns, Optional<FileType> fileType, Optional<String> gotoPath) {
        return list(PaginationAndSorting.DEFAULT_PAGINATION_AND_SORTING, folderPath, patterns, fileType, gotoPath).getFiles();
    }

    @Override
    public List<File> list(String folderPath) {
        return list(folderPath, Optional.<List<String>>empty(), Optional.<FileType>empty(), Optional.<String>empty());
    }

    private static class FileListRespone extends DsmWebapiResponse<File.FileList> {

        @JsonCreator
        public FileListRespone(
                @JsonProperty("success") boolean success,
                @JsonProperty("data") File.FileList data,
                @JsonProperty("error") Error error) {
            super(success, data, error);
        }
    }
}
