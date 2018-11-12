package ru.siblion.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.StandardPasswordEncoder;
//import ru.siblion.security.UserDetailsService;


@Configuration
//@EnableWebSecurity
public class SecurityConfig{ //extends WebSecurityConfigurerAdapter {
/*
   // private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new StandardPasswordEncoder("53cr3t");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        System.out.println(userDetailsService);
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(encoder());
    }*/

    /**
     *  Пример захаркоденного конфига
     *  @Override
     *  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
     *      auth.inMemoryAuthentication()
     *          .withUser("buzz")
     *              .password("infinity")
     *              .authorities("ROLE_USER")
     *          .and()
     *              .withUser("woody")
     *              .password("bullseye")
     *          .authorities("ROLE_USER");
     *  }
     */
}
