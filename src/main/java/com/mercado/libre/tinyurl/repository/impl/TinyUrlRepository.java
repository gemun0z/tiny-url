package com.mercado.libre.tinyurl.repository.impl;

import com.mercado.libre.tinyurl.dto.TinyUrl;
import com.mercado.libre.tinyurl.model.RequestModel;
import com.mercado.libre.tinyurl.model.ResponseModel;
import com.mercado.libre.tinyurl.repository.ITinyUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class TinyUrlRepository implements ITinyUrlRepository {

    private final String TINYURL_CACHE = "TINYURL";

    private final RedisTemplate<String, Object> redisTemplate;

    private HashOperations<String, String, TinyUrl> hashOperations;

    @PostConstruct
    private void initHashOperations() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Autowired
    public TinyUrlRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void save(TinyUrl tinyUrl) {
        hashOperations.put(TINYURL_CACHE, tinyUrl.getId(), tinyUrl);
    }

    @Override
    public TinyUrl findByHash(String hash) {
        return hashOperations.get(TINYURL_CACHE, hash);
    }

    @Override
    public void deleteByHash(String hash) {
        hashOperations.delete(TINYURL_CACHE, hash);
    }
}
