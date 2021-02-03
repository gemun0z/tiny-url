package com.mercado.libre.tinyurl.service.impl;

import com.google.common.hash.Hashing;
import com.mercado.libre.tinyurl.dto.TinyUrl;
import com.mercado.libre.tinyurl.exception.NotFoundException;
import com.mercado.libre.tinyurl.model.RequestModel;
import com.mercado.libre.tinyurl.repository.ITinyUrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TinyUrlServiceTest {

    private static final String URL = "https://articulo.mercadolibre.cl/MLC-562726671-apple-iphone-12-pro-max-256-gb-6-ram-homologados-_JM?searchVariation=68958758413#searchVariation=68958758413&position=3&type=item&tracking_id=1ff1f665-990a-4d9c-8227-2c791e1c19bd";
    private String hash;

    @Mock
    private ITinyUrlRepository repository;

    @InjectMocks
    private TinyUrlService service;

    @BeforeEach
    void setUp() {
        hash = Hashing.murmur3_32().hashString(URL, StandardCharsets.UTF_8).toString();
    }

    @Test
    void save() {

        TinyUrl tinyUrl = generateTinyUrl();

        lenient().doNothing().when(repository).save(tinyUrl);

        RequestModel request = RequestModel.builder()
                .url(URL)
                .build();

        service.save(request);

        verify(repository).save(refEq(tinyUrl, "createDatetime"));

    }

    @Test
    void findByHash() {

        TinyUrl tinyUrl = generateTinyUrl();

        when(repository.findByHash(hash)).thenReturn(tinyUrl);

        service.findByHash(hash);

        verify(repository).findByHash(hash);

    }

    @Test
    void findByHashNotFoundException() {

        given(repository.findByHash("some-hash")).willReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> service.findByHash("some-hash"));

        assertThat(exception).hasMessage("Not Found hash: some-hash");

    }

    @Test
    void findByTinyUrl() {

        TinyUrl tinyUrl = generateTinyUrl();

        when(repository.findByHash(hash)).thenReturn(tinyUrl);

        service.findByTinyUrl("http//:localhost:8080/" + hash);

        verify(repository).findByHash(hash);

    }

    @Test
    void findByTinyUrlNotFoundException() {

        given(repository.findByHash("some-hash")).willReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> service.findByTinyUrl("some-hash"));

        assertThat(exception).hasMessage("Not Found url: some-hash");

    }

    @Test
    void deleteByHash() {

        TinyUrl tinyUrl = generateTinyUrl();

        when(repository.findByHash(hash)).thenReturn(tinyUrl);

        lenient().doNothing().when(repository).deleteByHash(hash);

        service.deleteByHash(hash);

        verify(repository).deleteByHash(hash);

    }

    @Test
    void deleteByHashNotFoundException() {

        given(repository.findByHash("some-hash")).willReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> service.deleteByHash("some-hash"));

        assertThat(exception).hasMessage("Not Found hash: some-hash");

    }

    private TinyUrl generateTinyUrl(){
        return TinyUrl.builder()
                .id(hash)
                .url(URL)
                .createDatetime(LocalDateTime.now())
                .build();
    }
}