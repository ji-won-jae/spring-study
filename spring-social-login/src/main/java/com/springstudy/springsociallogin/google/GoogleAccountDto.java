package com.springstudy.springsociallogin.google;

import lombok.Data;

@Data
public class GoogleAccountDto {
    private String id;
    private String email;

    public static GoogleAccountDto of(String id, String email) {
        GoogleAccountDto googleAccountDto = new GoogleAccountDto();
        googleAccountDto.setId(id);
        googleAccountDto.setEmail(email);

        return googleAccountDto;
    }
}
