package com.example.mailservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.*;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;


@Service
@AllArgsConstructor
public class RabbitReceiver {
    private MailService mailService;

    @RabbitListener(queues = "mailConfirmationQueue")
    public void sendMailConfirmation(Message message){
        ObjectMapper objectMapper = new ObjectMapper();

        RabbitDTO rabbitDTO = null;
        try {
            rabbitDTO = objectMapper.readValue(message.getBody(), RabbitDTO.class);
            System.out.println(rabbitDTO);
        } catch (Exception e) {
            e.printStackTrace();

        }


        String url = rabbitDTO.getUrl();
        String temp = url.substring(url.lastIndexOf("/") + 1);
        if (temp.equals("resend-confirmation")){
            url = url.substring(0, url.lastIndexOf("/"));
        }
        String subject = "Подтверждение регистрации";
        String confirmationUrl = url + "/confirmation?token=" + rabbitDTO.getAccessToken();

        String confirmationMessage = "Чтобы подтвердить регистрацию, перейдите по следующей ссылке: " + confirmationUrl;

       // mailService.send(rabbitDTO.getUserEmail(),subject, message);
        mailService.send("eka.politykina@gmail.com",subject, confirmationMessage);


    }

}
