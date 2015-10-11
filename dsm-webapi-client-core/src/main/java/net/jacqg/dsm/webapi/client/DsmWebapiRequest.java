package net.jacqg.dsm.webapi.client;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DsmWebapiRequest {

    private final String api;
    private final String version;
    private final String path;
    private final String method;
    private final Map<String, String> parameters = new HashMap<>();

    public DsmWebapiRequest(String api, String version, String path, String method) {
        this.api = api;
        this.version = version;
        this.path = path;
        this.method = method;
    }

    public String getApi() {
        return api;
    }

    public String getVersion() {
        return version;
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getParameters() {
        return Collections.unmodifiableMap(parameters);
    }

    public DsmWebapiRequest parameter(String key, String value) {
        this.parameters.put(key, value);
        return this;
    }
}
