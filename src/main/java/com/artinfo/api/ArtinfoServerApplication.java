package com.artinfo.api;

import com.artinfo.api.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication()
@EnableConfigurationProperties(AppConfig.class)
public class ArtinfoServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArtinfoServerApplication.class, args);
	}

}
