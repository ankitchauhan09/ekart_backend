    package com.example.ekart_backend.config.web;

    import com.example.ekart_backend.security.JwtAuthenticationEntryPoint;
    import com.example.ekart_backend.security.JwtAuthenticationFilter;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.http.HttpMethod;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
    import org.springframework.web.bind.annotation.CrossOrigin;

    @EnableWebSecurity
    @Configuration
    public class WebSecurityConfig {

        @Autowired
        private CustomUserDetailsService userDetailsService;

        @Autowired
        private JwtAuthenticationEntryPoint authenticationEntryPoint;

        @Autowired
        private JwtAuthenticationFilter authenticationFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            return http.csrf(csrf -> csrf.disable())
//                    .cors(cors -> cors.)
                    .authorizeRequests(authorizeRequest ->
                            authorizeRequest
//                                    .requestMatchers("/api/v1/auth/register").permitAll()
                                    .requestMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll()
                                    .requestMatchers(HttpMethod.GET, "/api/category/").permitAll()
                                    .requestMatchers(HttpMethod.POST, "/api/product/**").permitAll()
                                    .requestMatchers(HttpMethod.GET, "/api/product/**").permitAll()
                                    .requestMatchers("/error").permitAll()
                                    .requestMatchers("/create-order").permitAll()
                                    .requestMatchers( "/api/product/count").permitAll()
                                    .requestMatchers(HttpMethod.POST).authenticated()
                                    .requestMatchers(HttpMethod.GET).authenticated()
                                    .anyRequest().permitAll()
                    )
                    .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                    .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .exceptionHandling(
                            exception ->
                                    exception.authenticationEntryPoint(this.authenticationEntryPoint)
                    )
                    .sessionManagement(session ->
                            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )
                    .build();
        }

        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder(){
            return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
            return configuration.getAuthenticationManager();
        }

    }
