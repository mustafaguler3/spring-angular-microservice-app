package com.example.OrderService.external.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

@Configuration
public class OAuthRequestInterceptor implements RequestInterceptor {

    @Autowired
    private OAuth2AuthorizedClientManager auth2AuthorizedClientManager;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization","Bearer " +auth2AuthorizedClientManager.authorize(OAuth2AuthorizeRequest.withClientRegistrationId("internal-client")
                .principal("internal")
                .build())
                .getAccessToken().getTokenValue());
    }

}






















