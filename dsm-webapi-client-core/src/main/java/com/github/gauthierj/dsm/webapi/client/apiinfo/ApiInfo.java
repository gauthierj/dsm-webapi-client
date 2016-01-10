package com.github.gauthierj.dsm.webapi.client.apiinfo;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApiInfo {

    private final String api;
    private final String minVersion;
    private final String maxVersion;
    private final String path;
    private final String requestFormat;

    public ApiInfo(String api, String minVersion, String maxVersion, String path, String requestFormat) {
        this.api = api;
        this.minVersion = minVersion;
        this.maxVersion = maxVersion;
        this.path = path.replaceAll("^_+", "").trim();
        this.requestFormat = requestFormat;
    }

    private ApiInfo(String api, ApiInfo source) {
        this(api, source.getMinVersion(), source.getMaxVersion(), source.getPath(), source.getRequestFormat());
    }

    @JsonCreator
    public ApiInfo(
            @JsonProperty("minVersion") String minVersion,
            @JsonProperty("maxVersion") String maxVersion,
            @JsonProperty("path") String path,
            @JsonProperty("requestFormat") String requestFormat) {
        this(null, minVersion, maxVersion, path, requestFormat);
    }

    public String getApi() {
        return api;
    }

    public String getMinVersion() {
        return minVersion;
    }

    public String getMaxVersion() {
        return maxVersion;
    }

    public String getPath() {
        return path;
    }

    public String getRequestFormat() {
        return requestFormat;
    }

    public static class ApiInfoList {

        private final List<ApiInfo> apiInfos = new ArrayList<>();

        @JsonAnySetter
        public void add(String key, ApiInfo value) {
            apiInfos.add(new ApiInfo(key, value));
        }

        public List<ApiInfo> getApiInfos() {
            return Collections.unmodifiableList(apiInfos);
        }
    }
}
