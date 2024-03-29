package com.example.mq.rocketmq.consumers;

import com.alibaba.fastjson.JSONObject;
import com.example.mq.common.model.Student;
import com.example.mq.rocketmq.config.JmsConfig;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.springframework.stereotype.Component;


@Component
public class ObjectConsumer {
    private DefaultMQPushConsumer consumer;
    private String consumerGroup = "object_group";

    public ObjectConsumer() throws Exception{
        consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(JmsConfig.NAME_SERVER);
        // 设置消费地点,从最后一个进行消费(其实就是消费策略)
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 订阅主题的哪些标签
        consumer.subscribe(JmsConfig.OBJECT_TOPIC, "*");

        consumer.registerMessageListener((MessageListenerConcurrently)
                (msgs, context) -> {
                    try {
                        // 获取Message
                        Message msg = msgs.get(0);
                        Student student = JSONObject.parseObject(msg.getBody(),Student.class);

                        System.out.printf("%s Receive New OBject: %s %n",
                                Thread.currentThread().getName(), student.toString());

                        String topic = msg.getTopic();
//                        String body = new String(msg.getBody(), "utf-8");
                        // 标签
                        String tags = msg.getTags();
                        String keys = msg.getKeys();
                        System.out.println("topic=" + topic + ", tags=" + tags + ",keys=" + keys + ", msg=" + student.getName());
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                });
        consumer.start();
        System.out.println("Object Listener");
    }
}
