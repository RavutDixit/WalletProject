package com.wallet.WalletProject.Service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean 
	public UserDetailsService userDetailsService()
	{
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	@Bean
	public DaoAuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setPasswordEncoder(passwordEncoder());
		authProvider.setUserDetailsService(userDetailsService());
		
		return authProvider;
	}
	
	@Bean 
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{
		http.authenticationProvider(authenticationProvider());
		http.authorizeRequests()
		    .antMatchers("/**").permitAll()
		    .antMatchers("/delete/**").hasAuthority("ADMIN")
		    .antMatchers("/edit/**").hasAuthority("ADMIN")
		    .anyRequest().authenticated()
		    .and()
		    .formLogin()
		    .loginPage("/login")
		    .defaultSuccessUrl("/index")
            .permitAll()
		    .and()
		    .logout().permitAll()
		    .and()
		    .exceptionHandling()
		    ;
		
		
		return http.build();
	}
	
	
}
