package com.springstudy.springfcm.firebase;

import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Slf4j
public class FcmService {

    private final String TOPIC_NAME = "Topic_Test";

    @Transactional
    public void sendMessage(FcmReqDto fcmReqDto) {
        try {
            Message.Builder messageBuilder = Message.builder()
                    .setToken(fcmReqDto.getTargetToken())
                    .putData("body", fcmReqDto.getData())
                    .setNotification(Notification.builder()
                            .setTitle("Firebase Notification Test")
                            .setBody(fcmReqDto.getData())
                            .build());

            FirebaseMessaging.getInstance().send(messageBuilder.build());

        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }


    @Transactional
    public void sendMessageToAllDevice(FcmReqDto fcmReqDto) {
        try {
            TopicManagementResponse responseTopic = FirebaseMessaging.getInstance().subscribeToTopic(
                    Collections.singletonList(fcmReqDto.getTargetToken()), TOPIC_NAME);

            log.info(" responseTopic ::: {}", responseTopic);
            log.info(" successCount ::: {}" ,responseTopic.getSuccessCount());


            Notification notification = Notification.builder()
                    .setTitle(TOPIC_NAME)
                    .setBody(fcmReqDto.getData())
                    .build();


            Message sendMessage = Message.builder()
                    .setTopic(TOPIC_NAME)
                    .setNotification(notification)
                    .putData("data", fcmReqDto.getData())
                    .build();

            FirebaseMessaging.getInstance().send(sendMessage);


        } catch (FirebaseMessagingException ex) {
            ex.printStackTrace();
        }
    }


}
