package com.mushr00m.service;

import com.mushr00m.entity.TbRoom;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DelayService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(int rid,Long MYTTL){
        MessagePostProcessor messagePostProcessor =
                new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        message.getMessageProperties().setExpiration(MYTTL+"");
                        return message;
                    }
                };

        rabbitTemplate.convertAndSend("deadLetterQueue",rid,messagePostProcessor);
    }
}
