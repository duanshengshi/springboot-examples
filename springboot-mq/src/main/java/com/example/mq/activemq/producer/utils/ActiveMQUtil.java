package com.example.mq.activemq.producer.utils;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;

import javax.jms.Queue;
import javax.jms.Topic;

public class ActiveMQUtil {


    public static Queue getMqQueue(String queueName){
        return new ActiveMQQueue(queueName);
    }

    public static Topic getMqTopic(String topicName){
        return new ActiveMQTopic(topicName);
    }
}
