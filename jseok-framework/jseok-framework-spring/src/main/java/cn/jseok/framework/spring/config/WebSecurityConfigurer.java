package cn.jseok.framework.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity(debug = true)
@ComponentScan(basePackages = "cn.jseok.security")
public class WebSecurityConfigurer {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource,DaoAuthenticationProvider daoAuthenticationProvider) throws Exception {
        http.authorizeHttpRequests(author -> author.requestMatchers("/index.jsp").permitAll().anyRequest().authenticated());
        http.csrf().disable();
        http.sessionManagement().disable();
        http.authenticationProvider(daoAuthenticationProvider);
        http.formLogin(form -> form.loginPage("/index.jsp").loginProcessingUrl("/user/login").failureForwardUrl("/failure.html").successForwardUrl("/success.html"));
        http.cors(cors -> cors.configurationSource(corsConfigurationSource));
        //http.formLogin().loginProcessingUrl("/user/login").failureHandler().successHandler();
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService jseokUserDetailsService, PasswordEncoder jseokPasswordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new  DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(jseokPasswordEncoder);
        daoAuthenticationProvider.setUserDetailsService(jseokUserDetailsService);
        return  daoAuthenticationProvider;
    }
}
