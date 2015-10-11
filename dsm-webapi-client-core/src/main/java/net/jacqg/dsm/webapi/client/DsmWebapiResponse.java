package net.jacqg.dsm.webapi.client;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

import static java.util.Optional.ofNullable;

public class DsmWebapiResponse<T> {

    private boolean success;
    private T data;
    private Error error;

    @JsonCreator
    public DsmWebapiResponse(@JsonProperty("success") boolean success,
                             @JsonProperty("data") T data,
                             @JsonProperty("error") Error error) {
        this.success = success;
        this.data = data;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public Error getError() {
        return error;
    }

    public static class Error {

        private final int code;
        private final List<Error> errors = new ArrayList<>();
        private final Map<String, String> details = new HashMap<>();

        @JsonCreator
        public Error(@JsonProperty("code") int code,
                     @JsonProperty("errors") List<Error> errors) {
            this.code = code;
            this.errors.addAll(ofNullable(errors).orElse(new ArrayList<>()));
        }

        public int getCode() {
            return code;
        }

        public List<Error> getErrors() {
            return Collections.unmodifiableList(errors);
        }

        public Map<String, String> getDetails() {
            return Collections.unmodifiableMap(details);
        }

        @JsonAnySetter
        public void addDetail(String key, String value) {
            this.details.put(key, value);
        }

        public String getDetailValue(String key) {
            return details.get(key);
        }
    }
}
