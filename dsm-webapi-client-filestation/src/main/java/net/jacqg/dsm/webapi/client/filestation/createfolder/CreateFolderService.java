package net.jacqg.dsm.webapi.client.filestation.createfolder;

import net.jacqg.dsm.webapi.client.filestation.filelist.File;

public interface CreateFolderService {

    void createFolder(String parentPath, String name);

    File createFolder(String parentPath, String name, boolean createParents);
}
