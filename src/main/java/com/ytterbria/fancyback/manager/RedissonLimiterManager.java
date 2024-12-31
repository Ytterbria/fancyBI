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
     *
     * @param key key区分不同的限流器,每个用户id分别统计
     * @param rate 限流阈值 表示令牌生成的速度,每秒生成多少个令牌
     * @param rateInterval 令牌生成的时间间隔，单位秒
     * @return 是否获得令牌
     */
    public boolean acquire(String key, int rate,int rateInterval) {


        // 创建限流器
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);

        // OVERALL 代表总体限流
        rateLimiter.trySetRate(RateType.OVERALL,rate,rateInterval, RateIntervalUnit.SECONDS);

        // tryAcquire(permits) 尝试获取指定数量的令牌，如果获取成功，则返回 true，否则返回 false
        return rateLimiter.tryAcquire(1);
    }
}
