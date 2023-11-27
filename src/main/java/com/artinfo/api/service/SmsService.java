package com.artinfo.api.service;

import com.artinfo.api.config.AppConfig;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SmsService {
  private final AppConfig appConfig;
  private DefaultMessageService messageService;


  @PostConstruct
  private void init(){
    this.messageService = NurigoApp.INSTANCE.initialize(appConfig.coolSms.get("key"), appConfig.coolSms.get("secret"), "https://api.coolsms.co.kr");
  }
  public void sendSms() {
    Message message = new Message();
    message.setFrom("01040287451");
    message.setTo("01040287451");
    message.setText("테스트");

    SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));

    log.info("response={}", response);
  }
}
