package com.springstudy.springstmp.stmp;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class EmailReqDto {

    private String to;
    private String subject;
    private String message;
}
