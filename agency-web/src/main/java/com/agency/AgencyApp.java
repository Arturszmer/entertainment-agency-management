package com.agency;

import com.agency.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class AgencyApp {
    public static void main(String[] args) {
        SpringApplication.run(AgencyApp.class, args);
    }

}
