package com.raktar2.security;


import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableGlobalMethodSecurity(securedEnabled=true)
@Configuration
public class SecurityConfig  extends WebSecurityConfigurerAdapter{

	@Override
	public void configure(HttpSecurity http) throws Exception{
		
		http.authorizeRequests()
			.antMatchers(HttpMethod.POST,"/").permitAll()
			.antMatchers("/datepicker").authenticated()
			.antMatchers("/datas").authenticated()
			.and()
			.formLogin().permitAll();
		
	}
	
	 
}
