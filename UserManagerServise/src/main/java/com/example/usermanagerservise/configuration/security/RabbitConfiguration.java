package com.example.usermanagerservise.configuration.security;

import lombok.Setter;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.example.RabbitDTO;
@Setter
@Configuration
public class RabbitConfiguration {
    @Value("${rabbitMQ.queue.name}")
    private String queueName;
    @Value("${rabbitMQ.exchange.name}")
    private String exchangeName;
    @Value("${rabbitMQ.routing.key}")
    private String routingKey;
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchangeName);
    }
    @Bean
    public Queue queue(){
        return new Queue(queueName, false);
    }
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue())
                .to(exchange())
                .with(routingKey);
    }
    @Bean
    public MessageConverter converter(){

        return  new Jackson2JsonMessageConverter();

    }
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
