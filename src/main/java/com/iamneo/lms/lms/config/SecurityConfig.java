package com.iamneo.lms.lms.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.iamneo.lms.lms.enumerated.Role;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final AuthenticationProvider authenticationProvider;
        private final JwtAuthenticationFilter jwtAuthenticationFilter;
        private final LogoutHandler logoutHandler;
        private static final String[] WHITELIST = {
                        "/lms/login",
                        "/swagger-ui/**",
                        "/swagger-ui.html/",
                        "/v3/api-docs/**"
        };

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
                return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                                .cors(cors -> corsConfigurationSource())
                                .authorizeHttpRequests(authorize -> authorize
                                                .requestMatchers("/lms/admin/**").hasRole(Role.ADMIN.name())
                                                .requestMatchers("/lms/teacher/**")
                                                .hasAnyRole(Role.ADMIN.name(), Role.TEACHER.name())
                                                .requestMatchers(WHITELIST)
                                                .permitAll()
                                                .anyRequest()
                                                .authenticated())
                                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                                .logout(logout -> logout.logoutUrl("/api/v1/auth/logout")
                                                .addLogoutHandler(logoutHandler)
                                                .logoutSuccessHandler(
                                                                (request, response,
                                                                                authentication) -> SecurityContextHolder
                                                                                                .clearContext()))
                                .build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration corsConfiguration = new CorsConfiguration();

                corsConfiguration.setAllowedMethods(Arrays.asList(
                                HttpMethod.GET.name(),
                                HttpMethod.POST.name(),
                                HttpMethod.PUT.name(),
                                HttpMethod.DELETE.name()));

                corsConfiguration.setAllowedHeaders(Arrays.asList(
                                HttpHeaders.AUTHORIZATION,
                                HttpHeaders.CONTENT_TYPE));

                // provide front-end derver link here
                corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));

                corsConfiguration.setAllowCredentials(true);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", corsConfiguration);

                return source;
        }
}