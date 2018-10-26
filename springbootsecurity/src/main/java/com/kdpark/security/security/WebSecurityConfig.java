package com.kdpark.security.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.kdpark.security.service.UserAuthenticationService;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
		
	@Autowired private UserAuthenticationService userDetailsService;	
	@Autowired private MyAccessDeniedHandler accessDeniedHandler;
	@Autowired private PasswordEncoder passwordEncoder;
	
	private String[] permitAllUrl = new String[]{"/img/**","/css/**","/js/**","/webjars/**","/login/**","/mobile/**","/telco/**","/appdata/**"}; 
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
				.antMatchers(HttpMethod.POST,"/Management/**").hasAnyRole("ADMIN","NAT")
				.antMatchers(permitAllUrl).permitAll()
				.anyRequest().authenticated()				
				.and()
			.headers()  //setting Content-Security-Policy
			    .frameOptions().sameOrigin()  
				.httpStrictTransportSecurity().disable()
				.and()
			.httpBasic()
				.and()	
			.exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler)
			    .accessDeniedPage("/403")			    
			    .and()
			.formLogin()
				.loginPage("/login")				
				.usernameParameter("username")
				.passwordParameter("password")				
				.defaultSuccessUrl("/main")
				.failureUrl("/loginError")				
				.permitAll()				
				.and()
			.logout()				
				.logoutUrl("/logout")
				.logoutSuccessUrl("/logoutSuccess")
				.deleteCookies("JSESSIONID") 
				.clearAuthentication(true)
				.invalidateHttpSession(true)				
				.permitAll()
				.and()
			.cors()
			    .and()				
		    .sessionManagement()
		    	.sessionFixation().newSession()
				.invalidSessionUrl("/")		
				.maximumSessions(30)				
				.expiredUrl("/");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
		
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/resources/**");
	}
	
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList(""));
		configuration.setAllowedMethods(Arrays.asList("POST"));		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);		
		return source;
	}

	
}
