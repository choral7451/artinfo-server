package com.artinfo.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "artinfo")
public class AppConfig {

  public Map<String, String> coolSms;
}
