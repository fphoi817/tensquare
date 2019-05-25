package com.tensquare.jms.controller;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SmsConsumer {

    @RabbitListener(queues = "tensquare.sms")
    @RabbitHandler
    public void sendSms(Map<String, String> map){

        System.out.println("手机号: "+ map.get("mobile"));
        System.out.println("验证码: "+ map.get("code"));
    }
}
