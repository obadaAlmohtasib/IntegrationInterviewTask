package com.digitinary.customers.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
public class FeignConfig {

	@Value("${digitinary.service.accounts.security.user.name}")
	private String USERNAME;

	@Value("${digitinary.service.accounts.security.user.password}")
	private String PASSWORD;
	
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                String auth = USERNAME + ":" + PASSWORD;
                String encodedAuth = Base64Coder.encodeString(auth);
                requestTemplate.header(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth);
            }
        };
    }
	
}
