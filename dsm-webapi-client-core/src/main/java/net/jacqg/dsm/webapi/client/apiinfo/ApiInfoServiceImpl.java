package net.jacqg.dsm.webapi.client.apiinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.jacqg.dsm.webapi.client.DsmWebapiClient;
import net.jacqg.dsm.webapi.client.DsmWebapiRequest;
import net.jacqg.dsm.webapi.client.DsmWebapiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApiInfoServiceImpl implements ApiInfoService {

    @Autowired
    @Qualifier("unauthenticated")
    private DsmWebapiClient restClient;

    @Override
    public List<ApiInfo> findAll() {
        DsmWebapiRequest request = new DsmWebapiRequest("SYNO.API.Info", "1", "query.cgi", "query")
                .parameter("query", "all");
        ApiInfoWebapiResponse response = restClient.call(request, ApiInfoWebapiResponse.class);
        if(response.isSuccess()) {
            return response.getData().getApiInfos();
        } else {
            // TODO handle errors properly
            throw new IllegalStateException();
        }
    }

    @Override
    public ApiInfo findOne(String api) {
        DsmWebapiRequest request = new DsmWebapiRequest("SYNO.API.Info", "1", "query.cgi", "query")
                .parameter("query", api);
        ApiInfoWebapiResponse response = restClient.call(request, ApiInfoWebapiResponse.class);
        if(response.isSuccess()) {
            List<ApiInfo> apiInfos = response.getData().getApiInfos();
            return !apiInfos.isEmpty() ? apiInfos.get(0) : null;
        } else {
            // TODO handle errors properly
            throw new IllegalStateException();
        }
    }

    private static class ApiInfoWebapiResponse extends DsmWebapiResponse<ApiInfo.ApiInfoList> {

        public ApiInfoWebapiResponse(@JsonProperty("success") boolean success, @JsonProperty("data") ApiInfo.ApiInfoList data, @JsonProperty("error") Error error) {
            super(success, data, error);
        }
    }
}
