package com.mercado.libre.tinyurl.repository;

import com.mercado.libre.tinyurl.dto.TinyUrl;

public interface ITinyUrlRepository {

    void save(TinyUrl tinyUrl);

    TinyUrl findByHash(String hash);

    void deleteByHash(String hash);

}
