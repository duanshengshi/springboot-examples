package com.example.mq.activemq.producer.controller;

import com.example.mq.activemq.producer.utils.ActiveMQUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/publish")
public class PublishController {
    @Autowired
    private JmsMessagingTemplate jms;


    @RequestMapping("/queue")
    public String queue(){

        for (int i = 0; i < 10 ; i++){
            jms.convertAndSend(ActiveMQUtil.getMqQueue("publish.queue"), "queue"+i);
        }

        return "queue 发送成功";
    }

    @JmsListener(destination = "out.queue")
    public void consumerMsg(String msg){
        System.out.println(msg);
    }

    @RequestMapping("/topic")
    public String topic(){

        for (int i = 0; i < 10 ; i++){
            jms.convertAndSend(ActiveMQUtil.getMqTopic("publish.topic"), "topic"+i);
        }

        return "topic 发送成功";
    }
}
