package com.example.booking.config;

import com.example.booking.security.JwtAuthenticationFilter;

import jakarta.servlet.Filter;

import org.springframework.context.annotation.*; import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; import org.springframework.security.config.annotation.web.builders.HttpSecurity; import org.springframework.security.config.http.SessionCreationPolicy; import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.security.authentication.AuthenticationProvider; import org.springframework.security.authentication.dao.DaoAuthenticationProvider; import org.springframework.security.authentication.AuthenticationManager; import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration; import com.example.booking.security.CustomUserDetailsService; import org.springframework.security.web.SecurityFilterChain; import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; import org.springframework.web.cors.*; import org.springframework.http.HttpStatus; import java.util.List;

@Configuration @EnableMethodSecurity
public class SecurityConfig{
 @Bean AuthenticationProvider authenticationProvider(CustomUserDetailsService uds,PasswordEncoder encoder){DaoAuthenticationProvider p=new DaoAuthenticationProvider(uds);p.setPasswordEncoder(encoder);return p;}
 @Bean AuthenticationManager authenticationManager(AuthenticationConfiguration c)throws Exception{return c.getAuthenticationManager();}
 @Bean SecurityFilterChain filterChain(HttpSecurity http,JwtAuthenticationFilter jwt)throws Exception{
  http.csrf(c->c.disable()).cors(c->c.configurationSource(cors())).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
   .authorizeHttpRequests(a->a.requestMatchers("/auth/login","/swagger-ui/**","/swagger-ui.html","/v3/api-docs/**","/error").permitAll().requestMatchers("/api/admin/**").hasRole("ADMIN").requestMatchers("/api/resources/**","/api/reservations/**").authenticated().anyRequest().authenticated())
   .exceptionHandling(e -> e.authenticationEntryPoint((req,res,ex)->res.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized")).accessDeniedHandler((req,res,ex)->res.sendError(HttpStatus.FORBIDDEN.value(), "Forbidden")))
   .addFilterBefore((Filter) jwt, UsernamePasswordAuthenticationFilter.class); return http.build();
 }
 @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
 @Bean CorsConfigurationSource cors(){CorsConfiguration c=new CorsConfiguration();c.setAllowedOrigins(List.of("http://localhost:3000","http://localhost:5173"));c.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));c.setAllowedHeaders(List.of("*"));c.setAllowCredentials(true);UrlBasedCorsConfigurationSource s=new UrlBasedCorsConfigurationSource();s.registerCorsConfiguration("/**",c);return s;}
}
