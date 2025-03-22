package org.example.springbank.config;

import org.example.springbank.models.Client;
import org.example.springbank.services.ClientService;
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
    public CommandLineRunner demo(final ClientService clientService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                Client client;

                for (int i = 0; i < 13; i++) {
                    client = new Client("Name" + i); //, "Surname" + i, "1234567" + i, "user" + i + "@test.com"
                    clientService.addClient(client);
                }
            }
        };
    }
}
