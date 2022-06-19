package com.nowcoder.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration
public class AlphaConfig {

    @Bean  //使SimpleDateFormat可以直接被容器调用，而不需要使用（AlphaService.class）这种模式
    //将SimpleDateFormat实例化一次，然后装配到了bean里，可供容器反复调用
    public SimpleDateFormat simpleDateFormat(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
