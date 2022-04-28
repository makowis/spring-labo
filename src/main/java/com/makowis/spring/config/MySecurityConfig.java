package com.makowis.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletResponse;
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
                .antMatchers("/api/auth/*").authenticated()
                .anyRequest().permitAll()
                .and()
            .csrf().disable();

        // 参考:https://fintan.jp/page/373/
        JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter =
                new JsonUsernamePasswordAuthenticationFilter(authenticationManager());
        jsonUsernamePasswordAuthenticationFilter.setUsernameParameter("email");
        jsonUsernamePasswordAuthenticationFilter.setPasswordParameter("password");
        // ログイン後にリダイレクトのリダイレクトを抑制
        jsonUsernamePasswordAuthenticationFilter
                .setAuthenticationSuccessHandler((req, res, auth) -> res.setStatus(HttpServletResponse.SC_OK));
        // ログイン失敗時のリダイレクト抑制
        jsonUsernamePasswordAuthenticationFilter
                .setAuthenticationFailureHandler((req, res, ex) -> res.setStatus(HttpServletResponse.SC_UNAUTHORIZED));

        // FormログインのFilterを置き換える
        http.addFilterAt(jsonUsernamePasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // Spring Securityデフォルトでは、アクセス権限（ROLE）設定したページに未認証状態でアクセスすると403を返すので、
        // 401を返すように変更
        http.exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
        // 今回は、403エラー時にHTTP Bodyを返さないように設定
        http.exceptionHandling().accessDeniedHandler((req, res, ex) -> res.setStatus(HttpServletResponse.SC_FORBIDDEN));

        // ログアウト
        http
                .logout()
                .logoutUrl("/logout")
                // ログアウト時のリダイレクト抑制
                .logoutSuccessHandler((req, res, auth) -> res.setStatus(HttpServletResponse.SC_OK))
                .invalidateHttpSession(true);
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
