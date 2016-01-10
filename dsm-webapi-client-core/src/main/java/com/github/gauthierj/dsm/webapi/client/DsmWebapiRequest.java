package com.github.gauthierj.dsm.webapi.client;

import com.google.common.base.Strings;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

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

    public DsmWebapiRequest parameter(String key, Object value) {
        this.parameters.put(key, value.toString());
        return this;
    }

    public <T> DsmWebapiRequest optionalParameter(String key, Optional<T> value, Function<T, String> toString) {
        if(value.isPresent()) {
            this.parameter(key, toString.apply(value.get()));
        }
        return this;
    }

    public <T> DsmWebapiRequest optionalParameter(String key, Optional<T> value) {
        return this.optionalParameter(key, value, T::toString);
    }

    public <T> DsmWebapiRequest optionalParameter(String key, T value, Predicate<T> shouldAdd) {
        if(shouldAdd.test(value)) {
            this.parameter(key, value);
        }
        return this;
    }

    public DsmWebapiRequest optionalStringParameter(String key, String value) {
        return this.optionalParameter(key, value, s -> !Strings.isNullOrEmpty(value));
    }
}
