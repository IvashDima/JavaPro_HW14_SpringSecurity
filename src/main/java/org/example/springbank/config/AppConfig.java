package org.example.springbank.config;

import org.example.springbank.enums.UserRole;
import org.example.springbank.services.DemoDataService;
import org.example.springbank.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppConfig extends GlobalMethodSecurityConfiguration implements WebMvcConfigurer {

    public static final String ADMIN_LOGIN = "admin";
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("/WEB-INF/static/");
    }
    @Bean
    public CommandLineRunner demo(final DemoDataService demoDataService,
                                  final UserService userService,
                                  final PasswordEncoder encoder) {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {

                userService.addUser(ADMIN_LOGIN,
                        encoder.encode("password"),
                        UserRole.ADMIN, "", "", "");
                userService.addUser("user",
                        encoder.encode("password"),
                        UserRole.USER, "", "", "");

                demoDataService.generateDemoData();

            }
        };
    }
}
