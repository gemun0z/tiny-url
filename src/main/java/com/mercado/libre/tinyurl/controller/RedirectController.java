package com.mercado.libre.tinyurl.controller;

import com.mercado.libre.tinyurl.model.ResponseModel;
import com.mercado.libre.tinyurl.service.ITinyUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Api(tags = {"Methods"})
@RestController
@RequestMapping
public class RedirectController {

    private final ITinyUrlService tinyUrlService;

    @Autowired
    public RedirectController(ITinyUrlService tinyUrlService) {
        this.tinyUrlService = tinyUrlService;
    }

    @ApiOperation(value = "Redirecciona URL corta hacia la URL original")
    @ApiResponses(value = {
            @ApiResponse(code = 308, message = "Permanent Redirect"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(path = "/{hash}")
    public void redirect(@PathVariable(value = "hash") String hash, HttpServletResponse httpServletResponse) {
        ResponseModel responseModel = tinyUrlService.findByHash(hash);
        httpServletResponse.setHeader("Location", responseModel.getUrl());
        httpServletResponse.setStatus(308);
    }

}
