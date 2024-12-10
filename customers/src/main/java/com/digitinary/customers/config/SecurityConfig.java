package com.digitinary.customers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(req -> {
					req.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll();  // Allow H2 console access
					req.requestMatchers(new AntPathRequestMatcher("/**")).permitAll();
				})
				.csrf(csrf -> csrf.disable())
				// Allow embedding the H2 console in a frame (important for H2 console)
				.headers(headers -> headers.frameOptions(frameOptionsCustomizer -> frameOptionsCustomizer.sameOrigin()))
				.build();
	}
	
}
