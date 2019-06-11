package com.example.mq.activemq.consumer.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class QueueListener {
    @JmsListener(destination = "publish.queue", containerFactory = "jmsListenerContainerQueue")
    @SendTo("out.queue")
    public String receive(String text){
        System.out.println("QueueListener: consumer-a 收到一条信息: " + text);
        return "consumer-a received : " + text;
    }

//    @JmsListener(destination = "out.queue", containerFactory = "jmsListenerContainerQueue")
////    @SendTo("out.queue")
//    public void receive1(String text){
//        System.out.println("QueueListener: consumer-b 收到一条信息: " + text);
//    }
}
