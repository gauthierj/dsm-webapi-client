package com.github.gauthierj.dsm.webapi.client.filestation.download;

import com.github.gauthierj.dsm.webapi.client.AbstractDsmServiceImpl;
import com.github.gauthierj.dsm.webapi.client.DsmWebapiRequest;
import com.github.gauthierj.dsm.webapi.client.filestation.exception.FileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class DownloadServiceImpl extends AbstractDsmServiceImpl implements DownloadService {

    // API Infos
    private static final String API_ID = "SYNO.FileStation.Download";
    private static final String API_VERSION = "1";

    // API Methods
    private static final String METHOD_DOWNLOAD = "download";

    // Parameters
    private static final String PARAMETER_MODE = "mode";
    private static final String PARAMETER_PATH = "path";

    // Parameters values
    private static final String PARAMETER_VALUE_OPEN = "open";

    @Autowired
    @Qualifier("downloadRestTemplate")
    private RestTemplate restTemplate;

    public DownloadServiceImpl() {
        super(API_ID);
    }

    @Override
    public byte[] download(String path) {
        try {
            DsmWebapiRequest request = new DsmWebapiRequest(getApiInfo().getApi(), API_VERSION, getApiInfo().getPath(), METHOD_DOWNLOAD)
                    .parameter(PARAMETER_PATH, path)
                    .parameter(PARAMETER_MODE, PARAMETER_VALUE_OPEN);
            URI uri = getDsmWebapiClient().buildUri(request);
            ResponseEntity<byte[]> response = restTemplate.getForEntity(uri, byte[].class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new FileNotFoundException(e, path);
        }
    }
}
