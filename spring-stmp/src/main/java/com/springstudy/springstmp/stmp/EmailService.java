package com.springstudy.springstmp.stmp;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    public void sendEmail(EmailReqDto emailReqDto) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            //메일 제목 설정
            helper.setSubject("메일 제목을 입력해주세요.");
            helper.setTo(emailReqDto.getTo());
            helper.setText(emailReqDto.getMessage());

            //변수 지정
            Context context = new Context();
            context.setVariable("code", emailReqDto.getMessage());

            //html template 주소 : email.html
            templateEngine.process("email", context);

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }
}
