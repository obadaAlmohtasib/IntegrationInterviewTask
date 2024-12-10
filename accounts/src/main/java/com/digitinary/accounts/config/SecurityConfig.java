package com.digitinary.accounts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
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
//					req.requestMatchers(new AntPathRequestMatcher("/**")).permitAll();
					req.anyRequest().authenticated();
				})
				.csrf(csrf -> csrf.disable())
				.httpBasic(Customizer.withDefaults())
				// Allow embedding the H2 console in a frame (important for H2 console)
				.headers(headers -> headers.frameOptions(frameOptionsCustomizer -> frameOptionsCustomizer.sameOrigin()))
				.build();
	}
	
}
