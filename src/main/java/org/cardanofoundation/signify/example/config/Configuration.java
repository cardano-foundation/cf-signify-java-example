package org.cardanofoundation.signify.example.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.cardanofoundation.signify.app.clienting.SignifyClient;
import org.cardanofoundation.signify.cesr.Salter;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public SignifyClient initSignifyClient() throws Exception {
        String url = "http://127.0.0.1:3901";
        String bootUrl = "http://127.0.0.1:3903";
        String bran = "0123456789abcdefghijk";
        SignifyClient client = new SignifyClient(url, bran, Salter.Tier.low, bootUrl, null);

        try {
            client.connect();
        } catch (Exception e) {
            client.boot();
            client.connect();
        }

        return client;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Signify API")
                        .version("1.0")
                        .description("API documentation for Signify Java interaction"));
    }
}
