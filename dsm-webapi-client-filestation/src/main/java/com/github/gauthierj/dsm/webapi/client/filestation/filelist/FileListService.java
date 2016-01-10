package com.github.gauthierj.dsm.webapi.client.filestation.filelist;

import com.github.gauthierj.dsm.webapi.client.filestation.common.File;
import com.github.gauthierj.dsm.webapi.client.filestation.common.FileType;
import com.github.gauthierj.dsm.webapi.client.filestation.common.PaginationAndSorting;

import java.util.List;
import java.util.Optional;

public interface FileListService {

    File.FileList list(PaginationAndSorting paginationAndSorting, String folderPath, Optional<List<String>> patterns, Optional<FileType> fileType, Optional<String> gotoPath);

    List<File> list(String folderPath, Optional<List<String>> patterns, Optional<FileType> fileType, Optional<String> gotoPath);

    List<File> list(String folderPath);

    List<File> getFiles(List<String> paths);

    File getFile(String path);
}
