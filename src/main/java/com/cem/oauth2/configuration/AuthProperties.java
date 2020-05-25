package com.cem.oauth2.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@Component
@ConfigurationProperties(prefix = "auth-config")
@Validated
public class AuthProperties {

    @NotNull
    private String clientSecret;

    @NotNull
    private String clientId;

    @NotNull
    private String tokenUri;

    @NotNull
    private String scope;
}
