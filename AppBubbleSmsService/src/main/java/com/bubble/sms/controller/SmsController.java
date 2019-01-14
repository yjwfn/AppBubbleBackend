//package com.bubble.sms.controller;
//
//
//import com.bubble.sms.service.SmsService;
//import com.bubble.sms.vo.SendSmsVO;
//import com.bubble.sms.vo.SmsTokenVO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/v1/sms")
//public class SmsController {
//
//
//    @Autowired
//    private SmsService smsService;
//
//
//    @PostMapping
//    public ResponseEntity<SmsTokenVO> sendSmsToPhone(
//            @RequestBody
//            @Validated SendSmsVO sendSmsVO, BindingResult bindingResult){
//
//        String token = smsService.sendRegistrySms(sendSmsVO.getPhoneExt(), sendSmsVO.getPhone());
//        return ResponseEntity.ok(new SmsTokenVO(token));
//    }
//}
