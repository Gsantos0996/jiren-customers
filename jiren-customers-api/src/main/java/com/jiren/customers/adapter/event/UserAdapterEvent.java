//package com.jiren.customers.adapter.event;
//
//import com.jiren.customers.domain.model.ExternalUser;
//import com.jiren.customers.service.dto.UserServiceDTO;
//import com.jiren.shared.kafka.service.dto.MessageDTO;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class UserAdapterEvent implements UserEvent{
//
//    private final KafkaTemplate<String, Object> kafkaTemplate;
//
//    @Value("${kafka.topic.users}")
//    private String usersTopic;
//
//    @Override
//    public void sendUserSavedEvent(MessageDTO<ExternalUser> messageDTO) {
//        kafkaTemplate.send(usersTopic, messageDTO);
//    }
//
//}
