package com.example.htmllandingservice.dto.Response;

import lombok.Data;

@Data
public class ResponseDTO<T> {
    T data;
    private ResponseMetaDTO responseMetaDTO;

    public ResponseDTO(){
        this.responseMetaDTO = new ResponseMetaDTO();
    }
}
