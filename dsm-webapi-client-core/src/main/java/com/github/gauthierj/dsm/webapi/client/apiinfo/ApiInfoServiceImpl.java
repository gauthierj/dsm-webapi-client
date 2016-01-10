package com.github.gauthierj.dsm.webapi.client.apiinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gauthierj.dsm.webapi.client.DsmWebapiResponse;
import com.github.gauthierj.dsm.webapi.client.DsmWebApiResponseError;
import com.github.gauthierj.dsm.webapi.client.DsmWebapiClient;
import com.github.gauthierj.dsm.webapi.client.DsmWebapiRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApiInfoServiceImpl implements ApiInfoService {

    private static final String FILESTATION_INFO_API = "SYNO.API.Info";
    private static final String FILESTATION_INFO_API_VERSION = "1";
    private static final String FILESTATION_INFO_API_PATH = "query.cgi";
    private static final String FILESTATION_INFO_API_LIST_METHOD = "query";
    private static final String ALL_FILES_PARAMETERS = "all";
    private static final String FILES_PARAMETERS = "query";

    @Autowired
    @Qualifier("unauthenticated")
    private DsmWebapiClient restClient;

    @Override
    public List<ApiInfo> findAll() {
        DsmWebapiRequest request = new DsmWebapiRequest(
                FILESTATION_INFO_API,
                FILESTATION_INFO_API_VERSION,
                FILESTATION_INFO_API_PATH,
                FILESTATION_INFO_API_LIST_METHOD)
                .parameter(FILES_PARAMETERS, ALL_FILES_PARAMETERS);
        ApiInfoWebapiResponse response = restClient.call(request, ApiInfoWebapiResponse.class);
        if(response.isSuccess()) {
            return response.getData().getApiInfos();
        } else {
            throw new AssertionError("Cannot happen");
        }
    }

    @Override
    public ApiInfo findOne(String api) {
        DsmWebapiRequest request = new DsmWebapiRequest(
                FILESTATION_INFO_API,
                FILESTATION_INFO_API_VERSION,
                FILESTATION_INFO_API_PATH,
                FILESTATION_INFO_API_LIST_METHOD)
                .parameter(FILESTATION_INFO_API_LIST_METHOD, api);
        ApiInfoWebapiResponse response = restClient.call(request, ApiInfoWebapiResponse.class);
        if(response.isSuccess()) {
            List<ApiInfo> apiInfos = response.getData().getApiInfos();
            if(apiInfos.isEmpty()) {
                throw new ApiNotFoundException(api);
            }
            return apiInfos.get(0);
        } else {
            throw new AssertionError("Cannot happen");
        }
    }

    private static class ApiInfoWebapiResponse extends DsmWebapiResponse<ApiInfo.ApiInfoList> {

        public ApiInfoWebapiResponse(@JsonProperty("success") boolean success, @JsonProperty("data") ApiInfo.ApiInfoList data, @JsonProperty("error") DsmWebApiResponseError error) {
            super(success, data, error);
        }
    }
}
