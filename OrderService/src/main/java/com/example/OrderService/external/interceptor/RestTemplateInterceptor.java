package com.example.OrderService.external.interceptor;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

import java.io.IOException;

public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {


    private OAuth2AuthorizedClientManager auth2AuthorizedClientManager;

    public RestTemplateInterceptor(OAuth2AuthorizedClientManager auth2AuthorizedClientManager) {
        this.auth2AuthorizedClientManager = auth2AuthorizedClientManager;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().add("Authorization","Bearer "+auth2AuthorizedClientManager.authorize(OAuth2AuthorizeRequest.withClientRegistrationId("internal-client")
                .principal("internal")
                .build())
                .getAccessToken().getTokenValue());

        return execution.execute(request,body);
    }

}





















