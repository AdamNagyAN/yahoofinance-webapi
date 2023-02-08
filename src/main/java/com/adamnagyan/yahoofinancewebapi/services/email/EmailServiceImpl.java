package com.adamnagyan.yahoofinancewebapi.services.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
  private final JavaMailSender mailSender;
  @Value("${spring.mail.sender}")
  private String from;

  @Override
  @Async
  public void send(String to, String email) {
    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper =
              new MimeMessageHelper(mimeMessage, "utf-8");
      helper.setText(email, true);
      helper.setTo(to);
      helper.setSubject("Confirm your email");
      helper.setFrom(from);
      log.info("Sending... from: " + from);
      mailSender.send(mimeMessage);
      log.info("Email sent to: " + to);
    } catch (MessagingException e) {
      log.error("Failed to send email", e);
    }
  }
}
