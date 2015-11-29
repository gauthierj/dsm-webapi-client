package net.jacqg.dsm.webapi.client.filestation.filelist;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Joiner;
import net.jacqg.dsm.webapi.client.AbstractDsmServiceImpl;
import net.jacqg.dsm.webapi.client.DsmWebapiRequest;
import net.jacqg.dsm.webapi.client.DsmWebapiResponse;
import net.jacqg.dsm.webapi.client.exception.DsmWebApiErrorException;
import net.jacqg.dsm.webapi.client.filestation.common.FileType;
import net.jacqg.dsm.webapi.client.filestation.common.PaginationAndSorting;
import net.jacqg.dsm.webapi.client.filestation.exception.FileNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FileListServiceImpl extends AbstractDsmServiceImpl implements FileListService {

    public FileListServiceImpl() {
        super("SYNO.FileStation.List");
    }

    @Override
    public File.FileList list(PaginationAndSorting paginationAndSorting, String folderPath, Optional<List<String>> patterns, Optional<FileType> fileType, Optional<String> gotoPath) {
        FileListResponse response = getDsmWebapiClient().call(
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
                , FileListResponse.class);
        handleErrors(response, folderPath);
        return response.getData();
    }

    @Override
    public List<File> list(String folderPath, Optional<List<String>> patterns, Optional<FileType> fileType, Optional<String> gotoPath) {
        return list(PaginationAndSorting.DEFAULT_PAGINATION_AND_SORTING, folderPath, patterns, fileType, gotoPath).getElements();
    }

    @Override
    public List<File> list(String folderPath) {
        return list(folderPath, Optional.<List<String>>empty(), Optional.<FileType>empty(), Optional.<String>empty());
    }

    @Override
    public List<File> getFiles(List<String> paths) {
        DsmWebapiRequest request = new DsmWebapiRequest(getApiId(), "1", getApiInfo().getPath(), "getinfo")
                .parameter("path", Joiner.on(',').join(paths))
                .parameter("additional", "real_path,size,owner,time,perm,type,mount_point_type");
        FileListResponse response = getDsmWebapiClient().call(request, FileListResponse.class);
        return response.getData().getElements()
                .stream()
                .filter(file -> file.getName() != null)
                .collect(Collectors.toList());
    }

    @Override
    public File getFile(String path) {
        List<File> files = getFiles(Collections.singletonList(path));
        return files.isEmpty() ? null : files.get(0);
    }

    private void handleErrors(FileListResponse response, String folderPath) {
        if(!response.isSuccess()) {
            int errorCode = response.getError().getCode();
            switch (errorCode) {
                case 408:
                    throw new FileNotFoundException(folderPath);
                default:
                    throw new DsmWebApiErrorException("An error occurred", errorCode);
            }
        }
    }

    private static class FileListResponse extends DsmWebapiResponse<File.FileList> {

        @JsonCreator
        public FileListResponse(
                @JsonProperty("success") boolean success,
                @JsonProperty("data") File.FileList data,
                @JsonProperty("error") Error error) {
            super(success, data, error);
        }
    }
}
