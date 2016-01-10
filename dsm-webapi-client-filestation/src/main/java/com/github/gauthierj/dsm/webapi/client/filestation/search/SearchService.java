package com.github.gauthierj.dsm.webapi.client.filestation.search;

import com.github.gauthierj.dsm.webapi.client.filestation.common.PaginationAndSorting;
import com.github.gauthierj.dsm.webapi.client.filestation.common.File;

import java.util.List;

public interface SearchService {

    String start(String searchedFolderPath, boolean recursive, SearchCriteria searchCriteria);

    boolean isFinished(String taskId);

    SearchResult getResult(String taskId, PaginationAndSorting paginationAndSorting);

    List<File> getResult(String taskId);

    void stop(List<String> taskIds);

    void stop(String taskId);

    void clean(List<String> taskIds);

    void clean(String taskId);

    List<File> synchronousSearch(String searchedFolderPath, boolean recursive, SearchCriteria searchCriteria);
}
