package com.cem.oauth2.controller;

import com.cem.oauth2.service.FeignServiceClient;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;

@RestController("/")
public class OAuthController {

    private final RestTemplate restTemplate;
    private final FeignServiceClient feignServiceClient;

    public OAuthController(RestTemplate restTemplate, FeignServiceClient feignServiceClient) {
        this.restTemplate = restTemplate;
        this.feignServiceClient = feignServiceClient;
    }

    @Value("${service-address}")
    public String serviceAddress;

    @GetMapping("/role1")
    @PreAuthorize("hasAnyRole('user-role')")
    public String userRoleService(Principal principal) {
        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) principal;
        return "Hello from userRole " + token.getName();
    }

    @GetMapping("/role2")
    @PreAuthorize("hasAnyRole('user2-role')")
    public String userRole2Service(Principal principal) {
        return "Hello from userRole2 " + principal.getName();
    }

    @GetMapping("test-rest-template")
    public String testRestTemplate(String service) {
        return restTemplate.getForEntity(serviceAddress + service, String.class).getBody();
    }

    @GetMapping("test-feign-1")
    public String testFeign1(String service) {
        return feignServiceClient.userRole1().getBody();
    }

    @GetMapping("test-feign-2")
    public String testFeign2(String service) {
        return feignServiceClient.userRole2().getBody();
    }

}
