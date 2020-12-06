package com.daw.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)	//Para habilitar la seguridad a nivel de método
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		 
	 //@Autowired
	 //private MyBasicAuthenticationEntryPoint authenticationEntryPoint;
	 @Autowired
	 private UserDetailsService userDetailsService;
	 @Autowired
	 private PasswordEncoder passwordEncoder;	

	//Tenemos que sobreescribir dos métodos: 
	//El que nos proporciona el AuthenticationManagerBuilder, que nos permitirá configurar la autenticación
	//El método configure con HTTPSecurity, que nos permitirá configurar el control de acceso
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors()
			.and()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
			.and()
			.httpBasic()
			.and()
			.authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS, "/users/**", "/roles/**", "/posts/**", "/categories/**", "/comments", "/comments/**", "/reports/**", "/advertisers/**" ).permitAll()								
				.antMatchers(HttpMethod.GET, "/categories/**", "/comments/**", "/posts/**" ).permitAll()
				.antMatchers(HttpMethod.POST, "/users/**").permitAll()
				.antMatchers("/administrador/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET, "/reports/**", "/roles/**", "/users", "/advertisers/**").hasRole("ADMIN")	
				.antMatchers(HttpMethod.GET, "/users/**").hasAnyRole("USER", "ADMIN") 
				.antMatchers(HttpMethod.POST, "/categories/**", "/posts/**", "/roles/**", "/advertisers/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.POST, "/comments/**").hasAnyRole("USER", "ADMIN")
				.antMatchers(HttpMethod.POST, "/reports/**").hasRole("USER")
				.antMatchers(HttpMethod.PUT, "/categories/**", "/posts/**", "/roles/**", "/advertisers/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.PUT, "/comments/**", "/users/**").hasAnyRole("USER", "ADMIN")
				.antMatchers(HttpMethod.DELETE, "/categories/**", "/posts/**", "/reports/**", "/roles/**", "/advertisers/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE, "/comments/**", "/users/**").hasAnyRole("USER", "ADMIN")			
				.anyRequest().authenticated()
			.and()
			.csrf().disable();
	}
	

}
