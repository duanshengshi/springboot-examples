package com.example.scheduled.dynamic;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
* <pre>
* @ClassName:       CronMapper
* @Description:     TODO -
* @Author:          dss
* @Date:            2019-05-31 11:43:07
* @Version:         1.0
* </pre>
*/
@Mapper
public interface CronMapper {
    @Select("select cron from cron limit 1")
    String getCron();
}
