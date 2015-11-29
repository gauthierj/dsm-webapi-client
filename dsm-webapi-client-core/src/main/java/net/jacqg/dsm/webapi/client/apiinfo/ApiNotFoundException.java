package net.jacqg.dsm.webapi.client.apiinfo;

import net.jacqg.dsm.webapi.client.exception.DsmWebApiClientException;

public class ApiNotFoundException extends DsmWebApiClientException {

    private final String apiId;

    public ApiNotFoundException(String apiId) {
        super(String.format("Api %s not found", apiId));
        this.apiId = apiId;
    }

    public String getApiId() {
        return apiId;
    }
}
