//package org.surveymonkey.kafka;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//public class Producer {
//
//    private final KafkaTemplate<String, String> kafkaTemplate;
//
//
//    @Value("EndUser")
//    private String topic;
//
//    Producer(KafkaTemplate<String, String> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    public void send(String topic, Message message) {
//        this.kafkaTemplate.send(topic, message.getMessage());
//        System.out.println("Sent sample message [" + message + "] to " + topic);
//    }
//
//}