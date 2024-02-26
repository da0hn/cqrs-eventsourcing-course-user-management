package dev.da0hn.user.cmd.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Bean
  public SecurityFilterChain httpSecurity(final HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
        authorizeRequests ->
          authorizeRequests
            .requestMatchers("/**").permitAll()
            .anyRequest()
            .authenticated()
      )
      .csrf(AbstractHttpConfigurer::disable);
    return http.build();
  }

}
