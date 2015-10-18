package net.jacqg.dsm.webapi.client.filestation.filelist;

import net.jacqg.dsm.webapi.client.filestation.common.PaginationAndSorting;

import java.util.List;
import java.util.Optional;

public interface FileListService {

    enum FileType {
        FILE("file"), DIRECTORY("directory"), ALL("all");

        private final String representation;

        FileType(String representation) {
            this.representation = representation;
        }

        public String getRepresentation() {
            return representation;
        }
    }

    File.FileList list(PaginationAndSorting paginationAndSorting, String folderPath, Optional<List<String>> patterns, Optional<FileType> fileType, Optional<String> gotoPath);

    List<File> list(String folderPath, Optional<List<String>> patterns, Optional<FileType> fileType, Optional<String> gotoPath);

    List<File> list(String folderPath);
}
