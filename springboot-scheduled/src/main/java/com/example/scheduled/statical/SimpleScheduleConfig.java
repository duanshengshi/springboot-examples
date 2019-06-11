package com.example.scheduled.statical;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

/**
* <pre>
* @ClassName:       SimpleScheduleConfig
* @Description:     TODO -  静态定时任务
* @Author:          dss
* @Date:            2019-05-31 11:56:12
* @Version:         1.0
* </pre>
*/
@Configuration //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling // 2.开启定时任务
public class SimpleScheduleConfig {
    //3.添加定时任务
    @Scheduled(cron = "0/5 * * * * ?")
    private void configureTasks() {
        System.err.println("执行静态定时任务: " + LocalDateTime.now());
    }
}
