package com.mercado.libre.tinyurl.controller;

import com.mercado.libre.tinyurl.model.ResponseModel;
import com.mercado.libre.tinyurl.service.ITinyUrlService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping(path = "/{hash}")
    public void redirect(@PathVariable(value = "hash") String hash, HttpServletResponse httpServletResponse) {
        ResponseModel responseModel = tinyUrlService.findByHash(hash);
        httpServletResponse.setHeader("Location", responseModel.getUrl());
        httpServletResponse.setStatus(302);
    }

}
