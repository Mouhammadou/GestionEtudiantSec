package org.gesec.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, DataSource dataSource) throws Exception{
        /*auth.inMemoryAuthentication().withUser("admin").password("{noop}1234").roles("ADMIN", "PROF");
        auth.inMemoryAuthentication().withUser("prof").password("{noop}1234").roles("PROF");
        auth.inMemoryAuthentication().withUser("etudiant").password("{noop}1234").roles("ETUDIANT");
        auth.inMemoryAuthentication().withUser("scolarite").password("{noop}1234").roles("SCOLARITE");*/
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .authoritiesByUsernameQuery("SELECT username as principal, {noop}password as credentials, true FROM users WHERE username = ?")
                .authoritiesByUsernameQuery("SELECT user_username as principal, roles_role as role FROM users_roles WHERE user_username = ?")
                .rolePrefix("ROLE_")
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/css/**", "/js/**", "/images/**").permitAll()
                    .anyRequest()
                        .authenticated()
                            .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                .defaultSuccessUrl("/index.html")
                            .and()
                .exceptionHandling().accessDeniedPage("/403.html");
    }
}
