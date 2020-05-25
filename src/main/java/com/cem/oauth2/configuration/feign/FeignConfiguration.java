package com.cem.oauth2.configuration.feign;

import com.cem.oauth2.configuration.AuthProperties;
import feign.RequestInterceptor;
import org.keycloak.OAuth2Constants;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

@Configuration
public class FeignConfiguration {

    private final AuthProperties properties;

    public FeignConfiguration(AuthProperties properties) {
        this.properties = properties;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), resource());
    }

    private OAuth2ProtectedResourceDetails resource() {
        ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
        resourceDetails.setGrantType(OAuth2Constants.CLIENT_CREDENTIALS);
        resourceDetails.setAccessTokenUri(properties.getTokenUri());
        resourceDetails.setClientId(properties.getClientId());
        resourceDetails.setClientSecret(properties.getClientSecret());
        return resourceDetails;
    }
}
