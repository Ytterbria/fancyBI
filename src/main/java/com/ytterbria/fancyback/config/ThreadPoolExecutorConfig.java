package com.ytterbria.fancyback.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolExecutorConfig {

     @Bean
     public ThreadPoolExecutor threadPoolExecutor(){
         ThreadFactory threadFactory = new ThreadFactory(){
             private int count = 1;
             @Override
             public Thread newThread(@NotNull Runnable r) {
                 Thread thread = new Thread(r);
                 thread.setName("fancyback-thread-" + count);
                 count++;
                 return thread;
             }
         };

         /*
           线程池参数
           corePoolSize：核心线程数
           maximumPoolSize：最大线程数
           keepAliveTime：线程空闲时间
           unit：线程空闲时间单位
           workQueue：任务队列
           threadFactory：线程工厂
           handler：拒绝策略
          */
         return new ThreadPoolExecutor(
                 5,
                 20,
                 60,
                 TimeUnit.SECONDS,
                 new ArrayBlockingQueue<>(4),
                 threadFactory
                 );
     }
}
