package com.mercado.libre.tinyurl.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseModel implements Serializable {

    @JsonProperty(value = "hash")
    private String hash;

    @JsonProperty(value = "tiny-url")
    private String tinyUrl;

    @JsonProperty(value = "url")
    private String url;

}
