package com.security.test_springsecurity.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //定制请求授权规则
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3");
        //开启自动配置的登录功能
        http.formLogin().usernameParameter("username").passwordParameter("password").loginPage("/userlogin");
        //开启自动配置的注销功能
        http.logout().logoutSuccessUrl("/");
        http.rememberMe().rememberMeParameter("remember");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //向内存中添加用户并给用户赋予相应的权限
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("张大").password(new  BCryptPasswordEncoder().encode("123"))
                        .roles("VIP1","VIP2").and()
                .withUser("张二").password(new  BCryptPasswordEncoder().encode("123"))
                        .roles("VIP2","VIP3").and()
                .withUser("张三").password(new  BCryptPasswordEncoder().encode("123"))
                        .roles("VIP1","VIP2","VIP3");
    }
}
