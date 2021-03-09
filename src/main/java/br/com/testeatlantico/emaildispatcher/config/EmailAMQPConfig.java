package br.com.testeatlantico.emaildispatcher.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailAMQPConfig {

    public static final String QUEUE = "emails-sent";
    public static final String EXCHANGE_NAME = "Emails";
    public static final String ROUTING_KEY = "";

    @Bean
    public Exchange declareExchange(){
        return ExchangeBuilder.directExchange(EXCHANGE_NAME)
                .durable(true)
                .build();
    }

    @Bean
    public Queue declareQueue(){
        return QueueBuilder.durable(QUEUE)
                .build();
    }

    @Bean
    public Binding declareBinding(Exchange exchange,Queue queue){
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(ROUTING_KEY)
                .noargs();
    }

}
