package com.artinfo.api.controller.sms;

import com.artinfo.api.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SmsController {

  private final SmsService smsService;

  @PostMapping("/sms")
  public void sendSms() {
    smsService.sendSms();
  }
}
