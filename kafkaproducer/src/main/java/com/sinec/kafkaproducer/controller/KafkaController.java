package com.sinec.kafkaproducer.controller;

import com.sinec.kafkaproducer.entity.DeviceList;
import com.sinec.kafkaproducer.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/kafka")
public class KafkaController {

//    @Autowired
//    private KafkaProducer kafkaProducer;
//
//    @Autowired
//    private DeviceList deviceList;


//    @PostMapping("/publish")
//    public ResponseEntity<Map<String, String>> publishToKafka(@RequestBody DeviceList device) {
//        kafkaProducer.sendDeviceLinkMessage(device);
//        Map<String, String> result = new HashMap<>();
//        result.put("RESULT", "success");
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

    public void sendRecords()
    {

        //kafkaProducer.sendDeviceLinkMessage(device);
    }
}
