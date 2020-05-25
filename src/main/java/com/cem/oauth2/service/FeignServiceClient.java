package com.cem.oauth2.service;

import com.cem.oauth2.configuration.feign.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "feign-service", url = "${service-address}", configuration = FeignConfiguration.class)
public interface FeignServiceClient {

    @GetMapping("/role1")
    ResponseEntity<String> userRole1();

    @GetMapping("/role2")
    ResponseEntity<String> userRole2();
}
