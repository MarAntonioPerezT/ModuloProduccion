package com.springApplication.moduloProduccion.views;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Configuration
public class Security extends VaadinWebSecurity {

    @Override
    protected void configure(HttpSecurity http) throws Exception{

        super.configure(http);
        setLoginView(http, Login.class);

    }

     @Bean
     public UserDetailsService userDetailsServiceBean() throws Exception{

            return  new InMemoryUserDetailsManager(

                    User.withUsername("Antonio")
                            .password("{noop}Antonio")
                            .roles("ADMIN")
                            .build(),
                    User.withUsername("Montse")
                            .password("{noop}Montse")
                            .roles("ADMIN")
                            .build()
            );
     }


}
