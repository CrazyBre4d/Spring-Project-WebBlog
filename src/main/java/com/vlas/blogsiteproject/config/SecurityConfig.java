package com.vlas.blogsiteproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/home","/registration").permitAll() // Разрешить доступ для всех к /home
                .antMatchers("/post", "/my-blog","/post", "/archive").authenticated() // Требовать аутентификации для /post и /page
                .and()
                .formLogin()
                //.loginPage("/login") // Указать страницу для входа
                .permitAll(); // Разрешить доступ к странице входа всем
                //.and()
                //.logout()
                //.logoutSuccessUrl("/")
               // .permitAll(); // Разрешить доступ всем к выходу
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}