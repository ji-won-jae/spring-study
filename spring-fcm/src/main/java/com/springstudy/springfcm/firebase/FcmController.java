package com.springstudy.springfcm.firebase;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FcmController {

    private final FcmService fcmService;

    //Target 대상 전송
    @PostMapping("/target")
    public ResponseEntity<String> pushToTargetDevice(@RequestBody FcmReqDto fcmReqDto) {
        fcmService.sendMessage(fcmReqDto);
        return ResponseEntity.ok("Success");
    }

    //Topic 대상 전송
    @PostMapping("/topic")
    public ResponseEntity<String> pushToTopic(@RequestBody FcmReqDto fcmReqDto) {
        fcmService.sendMessageToAllDevice(fcmReqDto);
        return ResponseEntity.ok("Success");
    }
}
