package com.mmm.movienight.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import com.mmm.movienight.services.MongoUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableConfigurationProperties
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final MongoUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfiguration(MongoUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //TODO: Research CSRF. Disable a security risk?
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/newuser","/user").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    //For custom login page
                    .loginPage("/login")
                    .permitAll()
                    .defaultSuccessUrl("/", true);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService);
    }


}
