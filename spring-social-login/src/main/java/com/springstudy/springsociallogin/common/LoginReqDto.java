package com.springstudy.springsociallogin.common;

import lombok.Data;

@Data
public class LoginReqDto {
    private SocialType socialType;
    private String identityToken;
}
