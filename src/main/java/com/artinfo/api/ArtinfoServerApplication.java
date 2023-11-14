package com.artinfo.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication()
public class ArtinfoServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArtinfoServerApplication.class, args);
	}

}
