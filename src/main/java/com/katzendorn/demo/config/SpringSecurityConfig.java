package com.katzendorn.demo.config;

import com.katzendorn.demo.config.handler.LoginSuccessHandler;
import com.katzendorn.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
//@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;

//    @Autowired
//    private AccessDeniedHandler accessDeniedHandler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/registration").not().fullyAuthenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user").hasRole("USER")
                .antMatchers("/", "/resources").permitAll()
                //use authentification
        .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(new LoginSuccessHandler())
//                .defaultSuccessUrl("/")
                .permitAll()
        .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/");
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }
}
