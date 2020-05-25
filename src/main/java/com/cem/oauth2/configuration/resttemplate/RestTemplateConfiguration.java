package com.cem.oauth2.configuration.resttemplate;

import com.cem.oauth2.configuration.AuthProperties;
import org.keycloak.OAuth2Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Configuration
public class RestTemplateConfiguration {
    private final AuthProperties properties;

    public RestTemplateConfiguration(AuthProperties properties) {
        this.properties = properties;
    }

    @Bean
    public RestTemplate restTemplateWithOAuth() {
        ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
        resourceDetails.setGrantType(OAuth2Constants.CLIENT_CREDENTIALS);
        resourceDetails.setAccessTokenUri(properties.getTokenUri());
        resourceDetails.setClientId(properties.getClientId());
        resourceDetails.setClientSecret(properties.getClientSecret());

        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails);
        AccessTokenProvider provider = new AccessTokenProviderChain(Arrays.asList(
                new ImplicitAccessTokenProvider(),
                new ResourceOwnerPasswordAccessTokenProvider(),
                new ClientCredentialsAccessTokenProvider()
        ));
        restTemplate.setAccessTokenProvider(provider);
        return restTemplate;
    }
}
