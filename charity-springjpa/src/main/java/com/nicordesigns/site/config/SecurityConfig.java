package com.nicordesigns.site.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .authorizeRequests()
	            .antMatchers("/login", "/resources/**").permitAll()
	            .antMatchers("/chat", "/chat/**", "/registration", "/registration/**", "/session", "/session/**").authenticated() // Add this line for chat, registration, and session
	            .anyRequest().authenticated()
	        .and()
	        .formLogin()
	            .loginPage("/login")
	            .defaultSuccessUrl("/registration/list")
	            .failureUrl("/login?error")
	            .permitAll()
	        .and()
	        .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout")
            .permitAll()
	        .and()
	        .csrf().disable()  // ✅ CSRF protection enabled by default
	        .sessionManagement()
	            .maximumSessions(1); // ✅ Limit concurrent sessions

	    return http.build();
	}

    @Bean
    public PasswordEncoder passwordEncoder() {
    	return NoOpPasswordEncoder.getInstance();
        //return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.setUsersByUsernameQuery(
            "SELECT USERNAME, PASSWORD, true as enabled FROM USER_ADMIN WHERE USERNAME = ?");
        users.setAuthoritiesByUsernameQuery(
            "SELECT USERNAME, 'ROLE_USER' FROM USER_ADMIN WHERE USERNAME = ?");
        return users;
    }
}