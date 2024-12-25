package com.ytterbria.fancyback.manager;

import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RedissonLimiterManager {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 限流操作
     * 令牌桶算法：
     * 1. 先获取令牌，如果没有令牌则等待，直到获取到令牌
     * 2. 获取到令牌后，判断是否超过限流限制，如果超过则丢弃令牌，如果没有超过则使用令牌
     * 3. 释放令牌，令牌桶算法中，令牌的数量是有限的，当令牌用完时，需要等待一段时间才能再次获取令牌
     *
     * @param key key区分不同的限流器,每个用户id分别统计
     * @param rate 限流阈值 表示令牌生成的速度,每秒生成多少个令牌
     * @param rateInterval 令牌生成的时间间隔，单位秒
     * @return 是否获得令牌
     */
    public boolean acquire(String key, int rate,int rateInterval) {


        // 创建限流器
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);

        // OVERALL 代表总体限流，每秒请求次数
        rateLimiter.trySetRate(RateType.OVERALL,rate,rateInterval, RateIntervalUnit.SECONDS);

        // 尝试获取令牌，成功返回 true，失败返回 false

        return rateLimiter.tryAcquire(1);
    }
}
