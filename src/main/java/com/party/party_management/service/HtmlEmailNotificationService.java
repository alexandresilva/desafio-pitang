package com.party.party_management.service;

import com.party.party_management.exception.NotificationException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class HtmlEmailNotificationService implements NotificationService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Override
    public void sendPaymentConfirmation(String email, String eventTitle, BigDecimal amount)
            throws NotificationException {
        try {
            Context context = new Context();
            context.setVariable("eventTitle", eventTitle);
            context.setVariable("amount", amount);
            context.setVariable("date", LocalDate.now());

            String htmlContent = templateEngine.process("email/payment-confirmation", context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("Confirmação de Pagamento");
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new NotificationException("Falha ao enviar email HTML", e);
        }
    }
}