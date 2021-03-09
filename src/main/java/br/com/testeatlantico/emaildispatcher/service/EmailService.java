package br.com.testeatlantico.emaildispatcher.service;

import br.com.testeatlantico.emaildispatcher.model.Email;
import com.fasterxml.jackson.core.JsonProcessingException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    @Qualifier("emailConfigBean")
    private Configuration emailConfig;

    public void send(Email email) throws MessagingException, IOException, TemplateException {

        Map model = new HashMap();
        model.put("content",email.getMessage());
        model.put("location","Brasil");
        model.put("sender",email.getSender());
        model.put("signature","https://www.atlantico.com.br/");

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        mimeMessageHelper.addInline("logo.png", new ClassPathResource("classpath:/logo.png"));

        Template template = emailConfig.getTemplate("email.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

        mimeMessageHelper.setTo(email.getDestination());
        mimeMessageHelper.setText(html, true);
        mimeMessageHelper.setSubject("API: Nova mensagem de " + email.getSender());
        mimeMessageHelper.setFrom("api@atlantico.com.br");

        javaMailSender.send(message);

            /*SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(email.getDestination());
            msg.setFrom("atlanticoapp@mail.com");
            msg.setSubject("API: Nova mensagem de "+ email.getSender());
            msg.setText(email.getMessage());

            javaMailSender.send(msg);*/
    }

}


