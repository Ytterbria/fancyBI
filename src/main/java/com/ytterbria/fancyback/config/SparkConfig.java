package com.ytterbria.fancyback.config;

import io.github.briqt.spark4j.SparkClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author leikooo
 * @Description 星火 AI 配置类
 */
@Configuration
@ConfigurationProperties(prefix = "xun-fei.client")
@Data
public class SparkConfig {
    @Bean
    public SparkClient sparkClient() {
        SparkClient sparkClient = new SparkClient();
        sparkClient.apiKey = "0a6889093ee29468138b4500c51fb9e1";
        sparkClient.apiSecret= "ZWZjOWY0YWQ0ZjgxMTYyZWY4NWE5ODlk";
        sparkClient.appid = "25e751a2";
        return sparkClient;
    }
}
