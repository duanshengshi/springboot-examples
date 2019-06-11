package com.example.mq.rocketmq.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.mq.common.model.Student;
import com.example.mq.rocketmq.config.JmsConfig;
import com.example.mq.rocketmq.producers.MyProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mq")
public class ProducerController {

    @Autowired
    private MyProducer producer;

    @GetMapping("send")
    public Object sendMsg(String text){
        // 创建消息  主题   二级分类   消息内容好的字节数组
        Message message = new Message(JmsConfig.TOPIC, "taga", ("hello rocketMQ " + text).getBytes());
        SendResult send = null;
        try {
            send = producer.getProducer().send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        producer.shutdown();
        System.out.println(send);
        return send;
    }

    @GetMapping("sendObj")
    public Object sendObj(String name){
        Student student = new Student();
        student.setName(name);
        student.setSex("man");
        student.setMobile("123456");
        student.setId(1);
        byte[] bytes = JSONObject.toJSONBytes(student);
        Message message = new Message(JmsConfig.OBJECT_TOPIC, JmsConfig.STUDENT_TAG, bytes);
        SendResult send = null;
        try {
            send = producer.getProducer().send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        producer.shutdown();
        System.out.println(send);
        return send;
    }
}
