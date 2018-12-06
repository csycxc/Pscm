package com.banry.pscm.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * Decorates a ClientHttpRequestFactory to maintain sessions (cookies)
 * to web servers.
 */
public class StatefulClientHttpRequestFactory implements ClientHttpRequestFactory {
    protected final Log logger = LogFactory.getLog(this.getClass());

    private final ClientHttpRequestFactory requestFactory;
    private final Map<String, String> hostToCookie = new HashMap<>();

    public StatefulClientHttpRequestFactory(ClientHttpRequestFactory requestFactory){
        this.requestFactory = requestFactory;
    }

    @Override
    public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {

        ClientHttpRequest request = requestFactory.createRequest(uri, httpMethod);

        final String host = request.getURI().getHost();
        String cookie = getCookie(host);
        if(cookie != null){
            logger.debug(format("Setting request Cookie header to [%s]", cookie));
            request.getHeaders().set("Cookie", cookie);
        }

        //decorate the request with a callback to process 'Set-Cookie' when executed
        return new CallbackClientHttpRequest(request, response -> {
            List<String> responseCookie = response.getHeaders().get("Set-Cookie");
            if(responseCookie != null){
                setCookie(host, responseCookie.stream().collect(Collectors.joining("; ")));
            }
            return response;
        });
    }

    private synchronized String getCookie(String host){
        String cookie = hostToCookie.get(host);
        return cookie;
    }

    private synchronized void setCookie(String host, String cookie){
        hostToCookie.put(host, cookie);
    }

    private static class CallbackClientHttpRequest implements ClientHttpRequest{

        private final ClientHttpRequest request;
        private final Function<ClientHttpResponse, ClientHttpResponse> filter;

        public CallbackClientHttpRequest(ClientHttpRequest request, Function<ClientHttpResponse, ClientHttpResponse> filter){
            this.request = request;
            this.filter = filter;
        }

        @Override
        public ClientHttpResponse execute() throws IOException {
            ClientHttpResponse response = request.execute();
            return filter.apply(response);
        }

        @Override
        public OutputStream getBody() throws IOException {
            return request.getBody();
        }

        @Override
        public HttpMethod getMethod() {
            return request.getMethod();
        }

        @Override
        public URI getURI() {
            return request.getURI();
        }

        @Override
        public HttpHeaders getHeaders() {
            return request.getHeaders();
        }

		@Override
		public String getMethodValue() {
			// TODO Auto-generated method stub
			return null;
		}
    }
}