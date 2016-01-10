package com.github.gauthierj.dsm.webapi.client.filestation.search;

import com.github.gauthierj.dsm.webapi.client.filestation.common.FileType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SearchCriteria {

    private List<String> patterns = new ArrayList<>();
    private List<String> extensions = new ArrayList<>();
    private Optional<FileType> fileType = Optional.empty();
    private Optional<Long> sizeFrom = Optional.empty();
    private Optional<Long> sizeTo = Optional.empty();
    private Optional<LocalDateTime> lastModifiedTimeFrom = Optional.empty();
    private Optional<LocalDateTime> lastModifiedTimeTo = Optional.empty();
    private Optional<LocalDateTime> creationTimeFrom = Optional.empty();
    private Optional<LocalDateTime> creationTimeTo = Optional.empty();
    private Optional<LocalDateTime> lastAccessTimeFrom = Optional.empty();
    private Optional<LocalDateTime> lastAccessTimeTo = Optional.empty();
    private Optional<String> ownerUserName = Optional.empty();
    private Optional<String> groupName = Optional.empty();

    private SearchCriteria() {
        super();
    }

    private SearchCriteria(SearchCriteria searchCriteria) {
        this();
        this.patterns.addAll(searchCriteria.patterns);
        this.extensions.addAll(searchCriteria.extensions);
        this.fileType = searchCriteria.fileType;
        this.sizeFrom = searchCriteria.sizeFrom;
        this.sizeTo = searchCriteria.sizeTo;
        this.lastModifiedTimeFrom = searchCriteria.lastModifiedTimeFrom;
        this.lastModifiedTimeTo = searchCriteria.lastModifiedTimeTo;
        this.creationTimeFrom = searchCriteria.creationTimeFrom;
        this.creationTimeTo = searchCriteria.creationTimeTo;
        this.lastAccessTimeFrom = searchCriteria.lastAccessTimeFrom;
        this.lastAccessTimeTo = searchCriteria.lastAccessTimeTo;
        this.ownerUserName = searchCriteria.ownerUserName;
        this.groupName = searchCriteria.groupName;
    }

    public List<String> getPatterns() {
        return Collections.unmodifiableList(patterns);
    }

    public List<String> getExtensions() {
        return Collections.unmodifiableList(extensions);
    }

    public Optional<FileType> getFileType() {
        return fileType;
    }

    public Optional<Long> getSizeFrom() {
        return sizeFrom;
    }

    public Optional<Long> getSizeTo() {
        return sizeTo;
    }

    public Optional<LocalDateTime> getLastModifiedTimeFrom() {
        return lastModifiedTimeFrom;
    }

    public Optional<LocalDateTime> getLastModifiedTimeTo() {
        return lastModifiedTimeTo;
    }

    public Optional<LocalDateTime> getCreationTimeFrom() {
        return creationTimeFrom;
    }

    public Optional<LocalDateTime> getCreationTimeTo() {
        return creationTimeTo;
    }

    public Optional<LocalDateTime> getLastAccessTimeFrom() {
        return lastAccessTimeFrom;
    }

    public Optional<LocalDateTime> getLastAccessTimeTo() {
        return lastAccessTimeTo;
    }

    public Optional<String> getOwnerUserName() {
        return ownerUserName;
    }

    public Optional<String> getGroupName() {
        return groupName;
    }

    public static class SearchCriteriaBuilder {

        private SearchCriteria template = new SearchCriteria();

        private SearchCriteriaBuilder() {
            super();
        }

        public static SearchCriteriaBuilder create() {
            return new SearchCriteriaBuilder();
        }

        public SearchCriteriaBuilder pattern(String pattern) {
            this.template.patterns.add(pattern);
            return this;
        }

        public SearchCriteriaBuilder extension(String extension) {
            this.template.extensions.add(extension);
            return this;
        }

        public SearchCriteriaBuilder fileType(FileType fileType) {
            this.template.fileType = Optional.of(fileType);
            return this;
        }

        public SearchCriteriaBuilder sizeFrom(long sizeFrom) {
            this.template.sizeFrom = Optional.of(sizeFrom);
            return this;
        }

        public SearchCriteriaBuilder sizeTo(long sizeTo) {
            this.template.sizeTo = Optional.of(sizeTo);
            return this;
        }

        public SearchCriteriaBuilder lastModifiedTimeFrom(LocalDateTime lastModifiedTimeFrom) {
            this.template.lastModifiedTimeFrom = Optional.of(lastModifiedTimeFrom);
            return this;
        }

        public SearchCriteriaBuilder lastModifiedTimeto(LocalDateTime lastModifiedTimeTo) {
            this.template.lastModifiedTimeTo = Optional.of(lastModifiedTimeTo);
            return this;
        }

        public SearchCriteriaBuilder creationTimeFrom(LocalDateTime creationTimeFrom) {
            this.template.creationTimeFrom = Optional.of(creationTimeFrom);
            return this;
        }

        public SearchCriteriaBuilder creationTimeTo(LocalDateTime creationTimeTo) {
            this.template.creationTimeTo = Optional.of(creationTimeTo);
            return this;
        }

        public SearchCriteriaBuilder lastAccessTimeFrom(LocalDateTime lastAccessTimeFrom) {
            this.template.lastAccessTimeFrom = Optional.of(lastAccessTimeFrom);
            return this;
        }

        public SearchCriteriaBuilder lastAccessTimeTo(LocalDateTime lastAccessTimeTo) {
            this.template.lastAccessTimeTo = Optional.of(lastAccessTimeTo);
            return this;
        }

        public SearchCriteriaBuilder ownerUserName(String ownerUserName) {
            this.template.ownerUserName = Optional.of(ownerUserName);
            return this;
        }

        public SearchCriteriaBuilder groupName(String groupName) {
            this.template.groupName = Optional.of(groupName);
            return this;
        }

        public SearchCriteria build() {
            return new SearchCriteria(template);
        }
    }
}
