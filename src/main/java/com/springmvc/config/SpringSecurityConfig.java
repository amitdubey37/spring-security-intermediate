package com.springmvc.config;

import com.springmvc.entity.User;
import com.springmvc.repositories.UserRepository;
import com.springmvc.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.intercept.RunAsImplAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;


@EnableWebSecurity()
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;

    @Autowired
    CustomUserDetailService customUserDetailService;

    @Autowired
    UserRepository userRepository;
    @PostConstruct
    void postConstruct() {
        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder().encode("pass"));
        userRepository.save(user);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder managerBuilder) throws Exception {
        managerBuilder
                .authenticationProvider(daoProvider())
                .authenticationProvider(runAsProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/doLogout","GET"));


    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }
    @Bean
    AuthenticationProvider daoProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(customUserDetailService);
        return daoAuthenticationProvider;
    }

    @Bean
    AuthenticationProvider runAsProvider() {
        RunAsImplAuthenticationProvider runAsImplAuthenticationProvider = new RunAsImplAuthenticationProvider();
        runAsImplAuthenticationProvider.setKey("MyRunAsKey");
        return runAsImplAuthenticationProvider;
    }


}
