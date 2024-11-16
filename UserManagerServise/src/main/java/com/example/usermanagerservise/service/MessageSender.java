package com.example.usermanagerservise.service;


import org.example.RabbitDTO;
import lombok.Setter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Setter
@Service
public class MessageSender {

    @Value("${rabbitMQ.exchange.name}")
    private String exchangeName;
    @Value("${rabbitMQ.routing.key}")
    private String routingKey;

    private RabbitTemplate rabbitTemplate;

    public MessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(RabbitDTO rabbitDTO){
        rabbitTemplate.convertAndSend(exchangeName, routingKey, rabbitDTO);
    }

}
