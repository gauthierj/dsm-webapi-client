package com.github.gauthierj.dsm.webapi.client.filestation.filelist;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gauthierj.dsm.webapi.client.DsmWebapiResponse;
import com.github.gauthierj.dsm.webapi.client.filestation.common.ErrorCodes;
import com.github.gauthierj.dsm.webapi.client.filestation.common.File;
import com.google.common.base.Joiner;
import com.github.gauthierj.dsm.webapi.client.AbstractDsmServiceImpl;
import com.github.gauthierj.dsm.webapi.client.DsmWebApiResponseError;
import com.github.gauthierj.dsm.webapi.client.DsmWebapiRequest;
import com.github.gauthierj.dsm.webapi.client.ErrorHandler;
import com.github.gauthierj.dsm.webapi.client.filestation.common.FileType;
import com.github.gauthierj.dsm.webapi.client.filestation.common.PaginationAndSorting;
import com.github.gauthierj.dsm.webapi.client.filestation.exception.FileNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FileListServiceImpl extends AbstractDsmServiceImpl implements FileListService {

    // API Infos
    private static final String API_ID = "SYNO.FileStation.List";
    private static final String API_VERSION = "1";

    // Methods
    private static final String METHOD_GET_INFO = "getinfo";
    private static final String METHOD_LIST = "list";

    // Parameters
    private static final String PARAMETER_ADDITIONAL = "additional";
    private static final String PARAMETER_FILETYPE = "filetype";
    private static final String PARAMETER_FOLDER_PATH = "folder_path";
    private static final String PARAMETER_GOTO_PATH = "goto_path";
    private static final String PARAMETER_LIMIT = "limit";
    private static final String PARAMETER_OFFSET = "offset";
    private static final String PARAMETER_PATH = "path";
    private static final String PARAMETER_PATTERN = "pattern";
    private static final String PARAMETER_SORT_BY = "sort_by";
    private static final String PARAMETER_SORT_DIRECTION = "sort_direction";

    // Parameter values
    private static final String PARAMETER_VALUE_ADDITIONAL = "real_path,size,owner,time,perm,type,mount_point_type";


    public FileListServiceImpl() {
        super(API_ID);
    }

    @Override
    public File.FileList list(PaginationAndSorting paginationAndSorting, String folderPath, Optional<List<String>> patterns, Optional<FileType> fileType, Optional<String> gotoPath) {
        DsmWebapiRequest request = new DsmWebapiRequest(getApiId(), API_VERSION, getApiInfo().getPath(), METHOD_LIST)
                .parameter(PARAMETER_FOLDER_PATH, folderPath)
                .parameter(PARAMETER_OFFSET, Integer.toString(paginationAndSorting.getOffset()))
                .parameter(PARAMETER_LIMIT, Integer.toString(paginationAndSorting.getLimit()))
                .parameter(PARAMETER_SORT_BY, paginationAndSorting.getSortBy().getRepresentation())
                .parameter(PARAMETER_SORT_DIRECTION, paginationAndSorting.getSortDirection().getRepresentation())
                .parameter(PARAMETER_PATTERN, Joiner.on(',').join(patterns.orElse(Collections.<String>emptyList())))
                .parameter(PARAMETER_FILETYPE, fileType.orElse(FileType.ALL).getRepresentation())
                .parameter(PARAMETER_GOTO_PATH, gotoPath.orElse(""))
                .parameter(PARAMETER_ADDITIONAL, PARAMETER_VALUE_ADDITIONAL);
        FileListResponse response = getDsmWebapiClient().call(request, FileListResponse.class, new FileListErrorHandler(folderPath));
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
        DsmWebapiRequest request = new DsmWebapiRequest(getApiId(), API_VERSION, getApiInfo().getPath(), METHOD_GET_INFO)
                .parameter(PARAMETER_PATH, Joiner.on(',').join(paths))
                .parameter(PARAMETER_ADDITIONAL, PARAMETER_VALUE_ADDITIONAL);
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

    private static class FileListResponse extends DsmWebapiResponse<File.FileList> {

        @JsonCreator
        public FileListResponse(
                @JsonProperty("success") boolean success,
                @JsonProperty("data") File.FileList data,
                @JsonProperty("error") DsmWebApiResponseError error) {
            super(success, data, error);
        }
    }

    private static class FileListErrorHandler implements ErrorHandler {
        private final String folderPath;

        public FileListErrorHandler(String folderPath) {
            this.folderPath = folderPath;
        }

        @Override
        public void handleError(DsmWebapiRequest request, DsmWebApiResponseError error) {
            if(error.getCode() == ErrorCodes.ERROR_CODE_NO_SUCH_FILE_OR_DIRECTORY) {
                throw new FileNotFoundException(request.getParameters().get(PARAMETER_FOLDER_PATH), error);
            }
        }
    }
}
