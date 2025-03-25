package org.example.springbank.config;

import org.example.springbank.enums.CurrencyType;
import org.example.springbank.models.Account;
import org.example.springbank.models.Client;
import org.example.springbank.services.AccountService;
import org.example.springbank.services.ClientService;
import org.example.springbank.services.DemoDataService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("/WEB-INF/static/");
    }
    @Bean
    public CommandLineRunner demo(final DemoDataService demoDataService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {

                demoDataService.generateDemoData();
            }
        };
    }
}
