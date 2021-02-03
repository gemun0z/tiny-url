package com.mercado.libre.tinyurl.controller;

import com.mercado.libre.tinyurl.model.RequestModel;
import com.mercado.libre.tinyurl.model.ResponseModel;
import com.mercado.libre.tinyurl.service.ITinyUrlService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = {"Methods"})
@RestController
@RequestMapping(value = "/api/v1")
public class TinyUrlController {

    private final ITinyUrlService tinyUrlService;

    @Autowired
    public TinyUrlController(ITinyUrlService tinyUrlService) {
        this.tinyUrlService = tinyUrlService;
    }

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel> createUrl(@RequestBody RequestModel requestModel) {
        ResponseModel responseModel = tinyUrlService.save(requestModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
    }

    @GetMapping(path = "/get/{hash}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel> getUrlByHash(@PathVariable(value = "hash") String hash) {
        ResponseModel responseModel = tinyUrlService.findByHash(hash);
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    @PostMapping(path = "/get", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel> getUrlByTinyUrl(@RequestBody RequestModel requestModel) {
        ResponseModel responseModel = tinyUrlService.findByTinyUrl(requestModel.getUrl());
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    @DeleteMapping(path = "/delete/{hash}")
    public ResponseEntity<Void> deleteUrlByHash(@PathVariable(value = "hash") String hash) {
        tinyUrlService.deleteByHash(hash);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
