package net.jacqg.dsm.webapi.client.filestation.download;

import net.jacqg.dsm.webapi.client.AbstractDsmServiceImpl;
import net.jacqg.dsm.webapi.client.DsmWebapiRequest;
import net.jacqg.dsm.webapi.client.filestation.exception.FileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class DownloadServiceImpl extends AbstractDsmServiceImpl implements DownloadService {

    @Autowired
    @Qualifier("downloadRestTemplate")
    private RestTemplate restTemplate;

    public DownloadServiceImpl() {
        super("SYNO.FileStation.Download");
    }

    @Override
    public byte[] download(String path) {
        try {
            DsmWebapiRequest request = new DsmWebapiRequest(getApiInfo().getApi(), "1", getApiInfo().getPath(), "download")
                    .parameter("path", path)
                    .parameter("mode", "open");
            URI uri = getDsmWebapiClient().buildUri(request);
            ResponseEntity<byte[]> response = restTemplate.getForEntity(uri, byte[].class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new FileNotFoundException(e, path);
        }
    }
}
