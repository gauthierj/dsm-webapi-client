package net.jacqg.dsm.webapi.client;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        ClientHttpResponse response = execution.execute(request, body);
        log(request,body,response);
        return response;
    }

    private void log(HttpRequest request, byte[] body, ClientHttpResponse response) {
        try {
            LOGGER.debug("Request Method: {}, Request Headers: {}, Request URI: {}", request.getMethod(), request.getHeaders(), request.getURI());
            LOGGER.debug("Request body: {}", new String(body));
            LOGGER.debug("Response body: {}", IOUtils.toString(response.getBody()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
