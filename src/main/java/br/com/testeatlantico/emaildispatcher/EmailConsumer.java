package br.com.testeatlantico.emaildispatcher;

import br.com.testeatlantico.emaildispatcher.config.EmailAMQPConfig;
import br.com.testeatlantico.emaildispatcher.model.Email;
import br.com.testeatlantico.emaildispatcher.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EmailConsumer {

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = EmailAMQPConfig.QUEUE)
    public void consumer(Message message) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Email email = mapper.readValue(message.getBody(),Email.class);
        emailService.send(email);
    }

}
