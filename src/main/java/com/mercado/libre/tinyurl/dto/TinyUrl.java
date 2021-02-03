package com.mercado.libre.tinyurl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TinyUrl implements Serializable {

    private String id;
    private String url;
    private LocalDateTime createDatetime;

}
