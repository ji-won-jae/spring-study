package com.springstudy.springfcm.firebase;

import lombok.Data;

@Data
public class FcmReqDto {

    private String data;

    private String targetToken;
}
