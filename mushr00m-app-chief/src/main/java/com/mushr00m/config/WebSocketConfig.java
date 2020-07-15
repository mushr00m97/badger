package com.mushr00m.config;

import com.mushr00m.websocket.BadgerHandShakeInterceptor;
import com.mushr00m.websocket.BadgerWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
public class WebSocketConfig implements WebSocketConfigurer {

    @Bean
    BadgerWebSocketHandler badgerWebSocketHandler(){
        return new BadgerWebSocketHandler();
    }

    @Bean
    BadgerHandShakeInterceptor badgerHandShakeInterceptor(){
        return new BadgerHandShakeInterceptor();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(badgerWebSocketHandler(),"/BadgerPush")
                .addInterceptors(badgerHandShakeInterceptor()).setAllowedOrigins("*");
    }
}
