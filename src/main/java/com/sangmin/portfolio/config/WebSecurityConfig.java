package com.sangmin.portfolio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.authentication.logout.DelegatingServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.SecurityContextServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.WebSessionServerLogoutHandler;

import com.sangmin.portfolio.model.enms.Role;
import com.sangmin.portfolio.service.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;


@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {//extends WebSecurityConfigurerAdapter {


	   private final CustomOAuth2UserService customOAuth2UserService;

	   @Bean
	   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		   

		   http
           .csrf().disable()
           .headers().frameOptions().disable()
           .and()
               .authorizeRequests()
               .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
               .antMatchers("/api/v1/**").hasRole(Role.USER.name())    
               .anyRequest().authenticated()   
           .and()
               .logout()
                   .logoutSuccessUrl("/").permitAll()
           
           .and()
               .oauth2Login() 
                   .userInfoEndpoint()
                       .userService(customOAuth2UserService);
		   
		    
	
		   

	       return http.build();
	   }
	   
//	    protected void configure(HttpSecurity http) throws Exception{
//	        http
//	                .csrf().disable()
//	                .headers().frameOptions().disable()
//	                .and()
//	                    .authorizeRequests()
//	                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
//	                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())    
//	                    .anyRequest().authenticated()   
//	                .and()
//	                    .logout()
//	                        .logoutSuccessUrl("/")
//	                .and()
//	                    .oauth2Login() 
//	                        .userInfoEndpoint()
//	                            .userService(customOAuth2UserService);
//	    }
}