package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
public class PostsApplication {
	public static void main(String[] args) {
		SpringApplication.run(PostsApplication.class, args);
	}

}
