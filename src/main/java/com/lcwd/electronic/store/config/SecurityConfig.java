package com.lcwd.electronic.store.config;

import com.lcwd.electronic.store.security.JwtAuthenticationEntryPoint;
import com.lcwd.electronic.store.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter authenticationFilter;


    /*@Bean
    public UserDetailsService userDetailsService() {
        // this User clas is of spring security(import org.springframework.security.core.userdetails.User;)

        //In memory mechanism ...because this data is in memory only
        //you can also implement database authentication system


        //without encoder
       *//* UserDetails normalUser = User.builder()
                .username("PRANAY")
                .password("pranay")
                .roles("NORMAL")
                .build();*//*

        //with encoder
        UserDetails normalUser = User.builder()
                .username("PRANAY")
                .password(passwordEncoder().encode("pranay"))
                .roles("NORMAL")
                .build();

        UserDetails adminUser = User.builder()
                .username("CHAITANYA")
                .password(passwordEncoder().encode("chaitanya"))
                .roles("ADMIN")
                .build();

        //create user
        // InMemoryUserDetailsManager -- it is a implementation class of UserDetailsService

        return new InMemoryUserDetailsManager(normalUser, adminUser);




    }*/


/*    //this is used for form based authentication
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("login.html")
                .loginProcessingUrl("/process-url")
                .defaultSuccessUrl("/dashboard")
                .failureUrl("error")
                .and()
                .logout()
                .logoutUrl("/logout");

        return http.build();

    }*/


    //basic authentication
    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .cors()
                .disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();


        return http.build();
    }
*/

    //JWT Authentication
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .cors()
                .disable()
                .authorizeRequests()
                .antMatchers("/auth/login")
                .permitAll()
                .antMatchers(HttpMethod.POST,"/users")
                .permitAll()
                .antMatchers(HttpMethod.DELETE,"/users/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();

    }
}
