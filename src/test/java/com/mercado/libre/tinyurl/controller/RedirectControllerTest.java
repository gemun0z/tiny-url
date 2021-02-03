package com.mercado.libre.tinyurl.controller;

import com.google.common.hash.Hashing;
import com.mercado.libre.tinyurl.exception.NotFoundException;
import com.mercado.libre.tinyurl.model.ResponseModel;
import com.mercado.libre.tinyurl.service.impl.TinyUrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RedirectController.class)
class RedirectControllerTest {

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
    void redirect() throws Exception {

        when(service.findByHash(hash)).thenReturn(generateResponseModel());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/{hash}", hash)).andReturn();

        assertEquals(mvcResult.getResponse().getStatus(), 308);
    }

    @Test
    void redirectNotFoundException() throws Exception {

        when(service.findByHash("some-hash")).thenThrow(NotFoundException.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/{hash}", "some-hash")).andReturn();

        assertEquals(mvcResult.getResponse().getStatus(), 404);
    }

    private ResponseModel generateResponseModel(){
        return ResponseModel.builder()
                .url(URL)
                .build();
    }
}