package com.stupidzhe.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by Mr.W on 2017/5/28.
 * 主程序
 */
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class StartApplication  {
    public static void main(String... args) {
        SpringApplication.run(StartApplication.class);
    }
}
