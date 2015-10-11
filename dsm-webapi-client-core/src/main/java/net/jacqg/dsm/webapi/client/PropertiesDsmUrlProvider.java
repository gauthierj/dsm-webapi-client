package net.jacqg.dsm.webapi.client;

import org.springframework.beans.factory.annotation.Value;

public class PropertiesDsmUrlProvider implements DsmUrlProvider {

    @Value("${dsm.webapi.scheme:http}")
    private String scheme;
    @Value("${dsm.webapi.host:diskstation.local}")
    private String host;
    @Value("${dsm.webapi.port:5000}")
    private String port;

    @Override
    public String getDsmUrl() {
        return String.format("%s://%s:%s", scheme, host, port);
    }
}
