package net.jacqg.dsm.webapi.client.filestation.download;

import net.jacqg.dsm.webapi.client.AbstractDsmServiceImpl;
import net.jacqg.dsm.webapi.client.DsmWebapiRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class DownloadServiceImpl extends AbstractDsmServiceImpl implements DownloadService {

    @Autowired
    @Qualifier("dsmWebapiClientRestTemplate")
    private RestTemplate restTemplate;

    public DownloadServiceImpl() {
        super("SYNO.FileStation.Download");
    }

    @Override
    public byte[] download(String path) {
        DsmWebapiRequest request = new DsmWebapiRequest(getApiInfo().getApi(), "1", getApiInfo().getPath(), "download")
                .parameter("path", path)
                .parameter("mode", "download");
        URI uri = getDsmWebapiClient().buildUri(request);
        ResponseEntity<byte[]> response = restTemplate.getForEntity(uri, byte[].class);
        return response.getBody();
    }
}
