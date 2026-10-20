
package com.placement.portal.config;
import org.springframework.beans.factory.annotation.Value;
import com.placement.portal.repository.StudentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public UserDetailsService userDetailsService(
            StudentRepository studentRepository,
            @Value("${ADMIN_USERNAME}") String adminUsername,
            @Value("${ADMIN_PASSWORD}") String adminPassword) {

        UserDetailsService studentService = username -> {
            var student = studentRepository.findByUsername(username)
                    .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

            return User.withUsername(student.getUsername())
                    .password(student.getPassword())
                    .roles("STUDENT")
                    .build();
        };

        var admin = User.withUsername(adminUsername)
                .password(passwordEncoder().encode(adminPassword))
                .roles("ADMIN")
                .build();

        var adminService = new InMemoryUserDetailsManager(admin);

        return username -> {
            if (adminUsername.equals(username)) {
                return adminService.loadUserByUsername(username);
            }
            return studentService.loadUserByUsername(username);
        };
    }

    

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/register", "/css/**")
                .permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/jobs", "/jobs/**", "/my-applications",
                                 "/aptitude", "/aptitude/**").hasRole("STUDENT")
                .anyRequest().authenticated()
        );

        http.formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
        );

        http.logout(logout -> logout.logoutSuccessUrl("/"));

        return http.build();
    }
}
