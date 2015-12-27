package net.jacqg.dsm.webapi.client.filestation.dirsize;

public interface DirSizeService {

    String start(String path);

    DirSizeResult status(String taskId);

    void stop(String id);

    DirSizeResult synchronousDirSize(String path);
}
