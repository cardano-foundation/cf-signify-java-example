package org.cardanofoundation.signify.example.config;

import org.cardanofoundation.signify.app.clienting.SignifyClient;
import org.cardanofoundation.signify.cesr.Salter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SignifyConfiguration {

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
}
