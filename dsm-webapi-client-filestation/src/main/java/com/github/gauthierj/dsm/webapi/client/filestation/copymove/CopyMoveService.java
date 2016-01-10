package com.github.gauthierj.dsm.webapi.client.filestation.copymove;

import com.github.gauthierj.dsm.webapi.client.filestation.common.OverwriteBehavior;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CopyMoveService {

    String startCopy(String path, String destinationFolderPath, OverwriteBehavior overwriteBehavior);

    String startMove(String path, String destinationFolderPath, OverwriteBehavior overwriteBehavior);

    String start(String path, String destinationFolderPath, OverwriteBehavior overwriteBehavior, boolean removeSource, boolean accurateProgress, Optional<String> searchTaskId);

    CopyMoveStatus status(String taskId);

    void stop(String taskId);

    void synchronousCopy(String path, String destinationFolderPath, OverwriteBehavior overwriteBehavior);

    void synchronousMove(String path, String destinationFolderPath, OverwriteBehavior overwriteBehavior);
}
