package com.mercado.libre.tinyurl.service.impl;

import com.google.common.hash.Hashing;
import com.mercado.libre.tinyurl.dto.TinyUrl;
import com.mercado.libre.tinyurl.exception.NotFoundException;
import com.mercado.libre.tinyurl.model.RequestModel;
import com.mercado.libre.tinyurl.model.ResponseModel;
import com.mercado.libre.tinyurl.repository.ITinyUrlRepository;
import com.mercado.libre.tinyurl.service.ITinyUrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Slf4j
@Service
public class TinyUrlService implements ITinyUrlService {

    private static final String PROTOCOL = "http";
    private static final String DOMAIN = "localhost";
    private static final String PORT = "8080";

    private final ITinyUrlRepository repository;

    @Autowired
    public TinyUrlService(ITinyUrlRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseModel save(RequestModel requestModel) {

        final String hashId = Hashing.murmur3_32().hashString(requestModel.getUrl(), StandardCharsets.UTF_8).toString();

        TinyUrl tinyUrl = TinyUrl.builder()
                .id(hashId)
                .url(requestModel.getUrl())
                .createDatetime(LocalDateTime.now())
                .build();

        repository.save(tinyUrl);

        return ResponseModel.builder()
                .tinyUrl(buildTinyUrl(tinyUrl.getId()))
                .build();
    }

    @Override
    public ResponseModel findByHash(String hash) {
        TinyUrl tinyUrl = repository.findByHash(hash);

        if (tinyUrl == null) {
            throw new NotFoundException("Not Found hash: " + hash, "/api/v1/get/" + hash);
        }

        return ResponseModel.builder()
                .url(tinyUrl.getUrl())
                .build();
    }

    @Override
    public ResponseModel findByTinyUrl(String url) {
        String [] temp = url.split("/");
        String hash = temp[temp.length-1];

        TinyUrl tinyUrl = repository.findByHash(hash);

        if (tinyUrl == null) {
            throw new NotFoundException("Not Found url: " + url, "/api/v1/get");
        }

        return ResponseModel.builder()
                .url(tinyUrl.getUrl())
                .build();
    }

    @Override
    public void deleteByHash(String hash) {
        TinyUrl tinyUrl = repository.findByHash(hash);

        if (tinyUrl == null) {
            throw new NotFoundException("Not Found hash: " + hash, "/api/v1/delete/" + hash);
        }

        repository.deleteByHash(hash);
    }

    private String buildTinyUrl(String hash) {
        return PROTOCOL + "://" + DOMAIN + ":" + PORT + "/" +hash;
    }
}
