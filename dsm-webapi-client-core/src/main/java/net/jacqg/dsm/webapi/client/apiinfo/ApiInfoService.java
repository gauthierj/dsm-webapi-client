package net.jacqg.dsm.webapi.client.apiinfo;

import java.util.List;

public interface ApiInfoService {
    List<ApiInfo> findAll();

    ApiInfo findOne(String api);
}
