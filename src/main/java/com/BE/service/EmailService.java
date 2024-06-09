package com.BE.service;



import com.BE.model.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@EnableAutoConfiguration
public class EmailService {
//    @Qualifier("TemplateEngine")
//    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;
//    public void sendMailTemplate(User user){
//        try{
//            Context context = new Context();
//            context.setVariable("name", user.getUsername());
//            context.setVariable("email", "http://mycremo.art/confirm-success?id="+user.getId()+"&email="+user.getFirstName());
//
//            String text = templateEngine.process("emailtemplate", context);
//
//            // Creating a simple mail message
//            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
//            // Setting up necessary details
//            mimeMessageHelper.setFrom("admin@gmail.com");
//            mimeMessageHelper.setTo(user.getLastName());
//            mimeMessageHelper.setText(text, true);
//            mimeMessageHelper.setSubject("Verify Account");
//            javaMailSender.send(mimeMessage);
//        }catch (MessagingException messagingException){
//            messagingException.printStackTrace();
//        }
//    }

    public void sendMail(User user, String subject, String description){

        try{
            // Creating a simple mail message
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            // Setting up necessary details
            mimeMessageHelper.setFrom("admin@gmail.com");
            mimeMessageHelper.setTo("tramctnse171174@fpt.edu.vn");
            mimeMessageHelper.setText(description);
            mimeMessageHelper.setSubject(subject);
            javaMailSender.send(mimeMessage);
        }catch (MessagingException messagingException){
            messagingException.printStackTrace();
        }
    }


}
