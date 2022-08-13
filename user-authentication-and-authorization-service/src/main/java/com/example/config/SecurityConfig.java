package com.example.config;

import com.example.filter.JwtFliter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final JwtFliter jwtFliter;
    private final String[] ALLOWED_ENDPOINTS = {"/swagger-ui.html", "/signup/**", "/login/**"};


    public SecurityConfig(UserDetailsService userDetailsService, JwtFliter jwtFliter) {
        this.userDetailsService = userDetailsService;
        this.jwtFliter = jwtFliter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtFliter, UsernamePasswordAuthenticationFilter.class);

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(ALLOWED_ENDPOINTS)
                .permitAll()
                .antMatchers("/users/super-admin/**")
                .hasRole("SUPER_ADMIN")
                .antMatchers("/users/admin/**")
                .hasAnyRole("ADMIN", "SUPER_ADMIN")
                .antMatchers("/users/manager/**")
                .hasAnyRole("MANAGER", "ADMIN", "SUPER_ADMIN")
                .antMatchers("/users/user/**")
                .hasAnyRole("USER", "MANAGER", "ADMIN", "SUPER_ADMIN")
                .and()
                .formLogin()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
