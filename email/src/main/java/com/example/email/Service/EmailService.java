package com.example.email.Service;

import com.example.email.DTO.MailBody;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendSimpleMessage(MailBody mailBody){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(mailBody.getTo());
        simpleMailMessage.setFrom("bahajaghoob10@gmail.com");
        simpleMailMessage.setSubject(mailBody.getSubject());
        simpleMailMessage.setText(mailBody.getText());

        javaMailSender.send(simpleMailMessage);

    }
}
