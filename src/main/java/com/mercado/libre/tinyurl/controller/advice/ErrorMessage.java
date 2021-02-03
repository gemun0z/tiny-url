package com.mercado.libre.tinyurl.controller.advice;

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
public class ErrorMessage implements Serializable {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

}
