package com.makowis.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@EnableWebSecurity
public class MySecurityConfig  extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder() {
        return  new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .authorizeRequests()
                .antMatchers(HttpMethod.GET,
                        "/*", "/static/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .defaultSuccessUrl("/", true)
                .permitAll()
                .and()
            .httpBasic()
                .and()
            .csrf().disable()
            .logout()
                .logoutSuccessUrl("/");
        // @formatter:on
    }
//
//    @Autowired
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // @formatter:off
//        auth.jdbcAuthentication()
//                .dataSource(this.dataSource)
//                .usersByUsernameQuery("SELECT user_id, password, enabled FROM Users WHERE user_id=?")
//                .passwordEncoder(passwordEncoder());
//        // @formatter:on
//    }
}
