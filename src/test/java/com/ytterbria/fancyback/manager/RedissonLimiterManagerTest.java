package com.ytterbria.fancyback.manager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedissonLimiterManagerTest {
    @Resource
    private RedissonLimiterManager redissonLimiterManager;
}