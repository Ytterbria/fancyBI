package com.ytterbria.fancyback.controller;

import cn.hutool.json.JSONUtil;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
@RequestMapping("/queue")
@Profile({"dev","local","test"})
public class QueueController {
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @GetMapping("/add")
    public void add(String name){
        CompletableFuture.runAsync(() -> {
            System.out.println("任务执行中: " + name + "执行者: " + Thread.currentThread().getName());
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }, threadPoolExecutor);
    }

    @GetMapping("/get")
    public String get(){
        Map<String,Object> map = new HashMap<>();
        map.put("队列长度",threadPoolExecutor.getQueue().size());
        map.put("线程池大小",threadPoolExecutor.getCorePoolSize());
        map.put("任务总数",threadPoolExecutor.getTaskCount());
        map.put("已经完成的任务数",threadPoolExecutor.getCompletedTaskCount());
        map.put("活跃线程数",threadPoolExecutor.getActiveCount());
        return JSONUtil.toJsonStr(map);
    }
}
