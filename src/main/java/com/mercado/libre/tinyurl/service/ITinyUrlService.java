package com.mercado.libre.tinyurl.service;

import com.mercado.libre.tinyurl.model.RequestModel;
import com.mercado.libre.tinyurl.model.ResponseModel;

public interface ITinyUrlService {

    ResponseModel save(RequestModel requestModel);

    ResponseModel findByHash(String hash);

    ResponseModel findByTinyUrl(String tinyUrl);

    void deleteByHash(String hash);

}
