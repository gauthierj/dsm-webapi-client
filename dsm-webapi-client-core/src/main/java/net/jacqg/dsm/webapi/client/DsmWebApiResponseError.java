package net.jacqg.dsm.webapi.client;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;

public class DsmWebApiResponseError {

    private final int code;
    private final List<DsmWebApiResponseError> errors = new ArrayList<>();
    private final Map<String, String> details = new HashMap<>();

    @JsonCreator
    public DsmWebApiResponseError(@JsonProperty("code") int code,
                                  @JsonProperty("errors") List<DsmWebApiResponseError> errors) {
        this.code = code;
        this.errors.addAll(ofNullable(errors).orElse(new ArrayList<>()));
    }

    public int getCode() {
        return code;
    }

    public List<DsmWebApiResponseError> getErrors() {
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

    @Override
    public String toString() {
        return "DsmWebApiResponseError{" +
                "code=" + code +
                ", errors=" + errors +
                ", details=" + details +
                '}';
    }
}
