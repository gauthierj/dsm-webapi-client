package com.github.gauthierj.dsm.webapi.client;

import com.github.gauthierj.dsm.webapi.client.apiinfo.ApiInfo;
import com.github.gauthierj.dsm.webapi.client.apiinfo.ApiInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public abstract class AbstractDsmServiceImpl {

    @Autowired
    private ApiInfoService apiInfoService;

    @Autowired
    private DsmWebapiClient dsmWebapiClient;

    private final String apiId;

    private ApiInfo apiInfo;

    public AbstractDsmServiceImpl(String apiId) {
        this.apiId = apiId;
    }

    @PostConstruct
    public final void init() {
        apiInfo = apiInfoService.findOne(apiId);
    }

    public DsmWebapiClient getDsmWebapiClient() {
        return dsmWebapiClient;
    }

    public ApiInfoService getApiInfoService() {
        return apiInfoService;
    }

    public String getApiId() {
        return apiId;
    }

    public ApiInfo getApiInfo() {
        return apiInfo;
    }
}
