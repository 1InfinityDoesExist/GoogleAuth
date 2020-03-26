package com.example.googleAuth.controller.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// TODO Auto-generated method stub

		httpSecurity.antMatcher("/**").authorizeRequests().antMatchers("/").permitAll().anyRequest().authenticated().and().oauth2Login().and().cors();
		
//		httpSecurity.antMatcher("/**").authorizeRequests().antMatchers("/").permitAll().anyRequest().authenticated()
//		.and().oauth2Login();

	}

}
