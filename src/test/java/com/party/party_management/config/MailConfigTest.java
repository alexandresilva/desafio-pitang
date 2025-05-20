package com.party.party_management.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MailConfigTest {

    @Autowired
    private JavaMailSender mailSender;

    @Test
    void contextLoads() {
        assertNotNull(mailSender);
    }
}