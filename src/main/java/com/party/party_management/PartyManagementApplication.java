package com.party.party_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.party.party_management")
public class PartyManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(PartyManagementApplication.class, args);
    }

}
