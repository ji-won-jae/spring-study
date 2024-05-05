package com.springstudy.springswaggertest.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SwaggerTestController {

    @Operation(summary = "check", description = "test api")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request Error"),
            @ApiResponse(responseCode = "404", description = "Not Found Error"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/check")
    public ResponseEntity<String> check(@Parameter(description = "이메일", required = true, example = "jwo9767@naver.com") @RequestParam String email) {
        return ResponseEntity.ok("test: " + email);
    }
}
