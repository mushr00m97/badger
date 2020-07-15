package com.mushr00m;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import tk.mybatis.spring.annotation.MapperScan;

@EnableWebSocket
@MapperScan(basePackages = "com.mushr00m.dao")
@SpringBootApplication
public class Start {
	public static void main(String[] args) {
		SpringApplication.run(Start.class, args);
	}
}
