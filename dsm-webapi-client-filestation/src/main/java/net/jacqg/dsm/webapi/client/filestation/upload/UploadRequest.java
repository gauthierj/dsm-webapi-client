package net.jacqg.dsm.webapi.client.filestation.upload;

import java.time.LocalDateTime;
import java.util.Optional;

public class UploadRequest {

    public enum OverwriteBehavior {
        OVERWRITE, SKIP, ERROR
    }

    private String parentFolderPath;
    private String fileName;
    private byte[] content;
    private OverwriteBehavior overwriteBehavior = OverwriteBehavior.ERROR;
    private boolean createParents = false;
    private Optional<LocalDateTime> lastModificationTime = Optional.empty();
    private Optional<LocalDateTime> creationTime = Optional.empty();
    private Optional<LocalDateTime> lastAccessTime = Optional.empty();

    private UploadRequest(String parentFolderPath, String fileName, byte[] content) {
        this.parentFolderPath = parentFolderPath;
        this.fileName = fileName;
        this.content = content;
    }

    private UploadRequest(UploadRequest uploadRequest) {
        this.parentFolderPath = uploadRequest.getParentFolderPath();
        this.fileName = uploadRequest.getFileName();
        this.content = uploadRequest.getContent();
        this.overwriteBehavior = uploadRequest.getOverwriteBehavior();
        this.createParents = uploadRequest.isCreateParents();
        this.lastAccessTime = uploadRequest.getLastAccessTime();
        this.creationTime = uploadRequest.getCreationTime();
        this.lastModificationTime = uploadRequest.getLastModificationTime();
    }

    public String getParentFolderPath() {
        return parentFolderPath;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getContent() {
        return content;
    }

    public OverwriteBehavior getOverwriteBehavior() {
        return overwriteBehavior;
    }

    public boolean isCreateParents() {
        return createParents;
    }

    public Optional<LocalDateTime> getLastModificationTime() {
        return lastModificationTime;
    }

    public Optional<LocalDateTime> getCreationTime() {
        return creationTime;
    }

    public Optional<LocalDateTime> getLastAccessTime() {
        return lastAccessTime;
    }

    public static UploadRequestBuilder createBuilder(String parentFolderPath, String fileName, byte[] content) {
        return new UploadRequestBuilder(parentFolderPath, fileName, content);
    }

    public static class UploadRequestBuilder {

        private UploadRequest template;

        private UploadRequestBuilder(String parentFolderPath, String fileName, byte[] content) {
            this.template = new UploadRequest(parentFolderPath, fileName, content);
        }

        public UploadRequestBuilder overwriteBehavior(OverwriteBehavior overwriteBehavior) {
            template.overwriteBehavior = overwriteBehavior;
            return this;
        }

        public UploadRequestBuilder createParents(boolean createParents) {
            template.createParents = createParents;
            return this;
        }

        public UploadRequestBuilder lastModificationTime(LocalDateTime lastModificationTime) {
            template.lastModificationTime = Optional.ofNullable(lastModificationTime);
            return this;
        }

        public UploadRequestBuilder creationTime(LocalDateTime creationTime) {
            template.creationTime = Optional.ofNullable(creationTime);
            return this;
        }

        public UploadRequestBuilder lastAccessTime(LocalDateTime lastAccessTime) {
            template.lastAccessTime = Optional.ofNullable(lastAccessTime);
            return this;
        }

        public UploadRequest build() {
            return new UploadRequest(template);
        }
    }
}
