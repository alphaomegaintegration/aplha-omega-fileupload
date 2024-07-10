package com.alpha.omega.fileupload.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.alpha.omega.fileupload.component.JwtAuthConverter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {
	private  JwtAuthConverter jwtAuthConverter;

	  @Bean
	  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	      http.cors().and().csrf().disable();
	      http.authorizeHttpRequests()
	              .requestMatchers("/api/v1/file*").hasRole("api-client")
	              .anyRequest().authenticated();
	      http.oauth2ResourceServer()
	              .jwt()
	              .jwtAuthenticationConverter(jwtAuthConverter);
	      http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	      return http.build();
	  }

}
