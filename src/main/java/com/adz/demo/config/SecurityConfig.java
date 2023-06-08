package com.adz.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("prod")
public class SecurityConfig
{

  @Bean
  public SecurityFilterChain configure(HttpSecurity http) throws Exception {
	  http
  		 	.authorizeHttpRequests((authorizeHttpRequests) ->
  		 		authorizeHttpRequests
  			 		.requestMatchers("/api/**").hasRole("USER_ROLE")
 			 		.requestMatchers("/swagger-ui/**").permitAll()
 			 		.requestMatchers("/v3/**").permitAll()
  		 	).httpBasic(Customizer.withDefaults());
	    
  		return http.build();
  }


  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    UserDetails user = User
        .withUsername("user")
        .password(passwordEncoder().encode("pass"))
        .roles("USER_ROLE")
        .build();
    return new InMemoryUserDetailsManager(user);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(8);
  }
}