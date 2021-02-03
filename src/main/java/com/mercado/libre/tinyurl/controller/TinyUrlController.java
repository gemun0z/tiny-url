package com.mercado.libre.tinyurl.controller;

import com.mercado.libre.tinyurl.model.RequestModel;
import com.mercado.libre.tinyurl.model.ResponseModel;
import com.mercado.libre.tinyurl.service.ITinyUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "Crea una URL corta dada una URL original")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = ResponseModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel> createUrl(@RequestBody RequestModel requestModel) {
        ResponseModel responseModel = tinyUrlService.save(requestModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
    }

    @ApiOperation(value = "Obtiene una URL original con un hash generado al crear la URL corta")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ResponseModel.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(path = "/get/{hash}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel> getUrlByHash(@PathVariable(value = "hash") String hash) {
        ResponseModel responseModel = tinyUrlService.findByHash(hash);
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    @ApiOperation(value = "Obtiene una URL original dada una URL corta")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ResponseModel.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping(path = "/get", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel> getUrlByTinyUrl(@RequestBody RequestModel requestModel) {
        ResponseModel responseModel = tinyUrlService.findByTinyUrl(requestModel.getUrl());
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    @ApiOperation(value = "Elimina una URL en la base de datos con un hash generado al crear la URL corta")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @DeleteMapping(path = "/delete/{hash}")
    public ResponseEntity<Void> deleteUrlByHash(@PathVariable(value = "hash") String hash) {
        tinyUrlService.deleteByHash(hash);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
