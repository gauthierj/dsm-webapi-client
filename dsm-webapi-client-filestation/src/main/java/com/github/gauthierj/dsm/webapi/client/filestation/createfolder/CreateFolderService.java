package com.github.gauthierj.dsm.webapi.client.filestation.createfolder;

import com.github.gauthierj.dsm.webapi.client.filestation.common.File;

public interface CreateFolderService {

    File createFolder(String parentPath, String name);

    File createFolder(String parentPath, String name, boolean createParents);
}
