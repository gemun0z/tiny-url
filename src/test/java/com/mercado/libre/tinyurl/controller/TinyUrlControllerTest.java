package com.mercado.libre.tinyurl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import com.mercado.libre.tinyurl.exception.NotFoundException;
import com.mercado.libre.tinyurl.model.RequestModel;
import com.mercado.libre.tinyurl.model.ResponseModel;
import com.mercado.libre.tinyurl.service.impl.TinyUrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TinyUrlController.class)
class TinyUrlControllerTest {

    private static final String URL = "https://articulo.mercadolibre.cl/MLC-562726671-apple-iphone-12-pro-max-256-gb-6-ram-homologados-_JM?searchVariation=68958758413#searchVariation=68958758413&position=3&type=item&tracking_id=1ff1f665-990a-4d9c-8227-2c791e1c19bd";
    private String hash;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TinyUrlService service;

    @BeforeEach
    void setUp() {
        hash = Hashing.murmur3_32().hashString(URL, StandardCharsets.UTF_8).toString();
    }

    @Test
    void createUrl() throws Exception {

        when(service.save(generateRequestModel())).thenReturn(generateResponseModelTinyUrl());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/create")
                .content(asJsonString(generateRequestModel()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tiny-url").exists());
    }

    @Test
    void getUrlByHash() throws Exception {

        when(service.findByHash(hash)).thenReturn(generateResponseModelUrl());

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/get/{hash}", hash)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.url").exists());

    }

    @Test
    void getUrlByHashNotFoundException() throws Exception {

        when(service.findByHash(hash)).thenThrow(NotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/get/{hash}", hash)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    void getUrlByTinyUrl() throws Exception {

        when(service.findByTinyUrl(anyString())).thenReturn(generateResponseModelUrl());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/get")
                .content(asJsonString(generateRequestModel()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.url").exists());

    }

    @Test
    void getUrlByTinyUrlNotFoundException() throws Exception {

        when(service.findByTinyUrl(anyString())).thenThrow(NotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/get")
                .content(asJsonString(generateRequestModel()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUrlByHash() throws Exception {

        lenient().doNothing().when(service).deleteByHash(hash);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/delete/{hash}", hash)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUrlByHashNotFoundException() throws Exception {

        doThrow(NotFoundException.class).when(service).deleteByHash(hash);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/delete/{hash}", hash)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ResponseModel generateResponseModelUrl(){
        return ResponseModel.builder()
                .url(URL)
                .build();
    }

    private ResponseModel generateResponseModelTinyUrl(){
        return ResponseModel.builder()
                .tinyUrl("http://localhost:8080/" + hash)
                .build();
    }

    private RequestModel generateRequestModel(){
        return RequestModel.builder()
                .url("some-url")
                .build();
    }
}