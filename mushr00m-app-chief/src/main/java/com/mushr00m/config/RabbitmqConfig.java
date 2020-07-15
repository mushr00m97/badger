package com.mushr00m.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitmqConfig {

    //死信交换机
    @Bean
    public DirectExchange delayExchange(){
        return new DirectExchange("exchange");
    }

    //用于延时消费的队列
    @Bean
    public Queue repeatTradeQueue() {
        Queue queue = new Queue("repeatTradeQueue", true, false, false);
        return queue;
    }

    //绑定交换机
    @Bean
    public Binding delayBinding(){
        return BindingBuilder.bind(repeatTradeQueue()).to(delayExchange())
                .with("repeatTradeQueue");
    }


    //死信队列
    @Bean
    public Queue deadLetterQueue(){
        Map<String,Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange","exchange");
        map.put("x-dead-letter-routing-key", "repeatTradeQueue");
        return new Queue("deadLetterQueue",true,false,false,map);
    }

}
