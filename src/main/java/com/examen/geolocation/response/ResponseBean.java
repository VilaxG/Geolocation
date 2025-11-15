package com.examen.geolocation.response;

import lombok.Data;

@Data
public class ResponseBean<T> {
    private Integer status;
    private String message;
    private T data;
}
