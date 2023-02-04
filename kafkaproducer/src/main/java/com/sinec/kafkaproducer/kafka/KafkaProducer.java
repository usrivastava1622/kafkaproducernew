package com.sinec.kafkaproducer.kafka;

//import com.fasterxml.jackson.databind.JsonSerializer;
import com.sinec.kafkaproducer.entity.DeviceList;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.List;
import java.util.Properties;
import java.util.Queue;
import java.util.SplittableRandom;
import java.util.concurrent.ArrayBlockingQueue;


public class KafkaProducer   {


    public void produceData(String ip1, String ip2) {
        int start=Integer.parseInt(ip1.split("[.]")[3]);
        int end=Integer.parseInt(ip2.split("[.]")[3]);
        System.out.println("start "+start+" "+"end "+end);
        DeviceList deviceList = new DeviceList();
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        for (int i = start; i <= end; i++) {
            StringBuilder builder = new StringBuilder("192.168.1.");
            String newchar = Integer.toString(i);
            builder.append(newchar);
            deviceList.setId(Long.parseLong(Integer.toString(i)));
            deviceList.setIpAddress(builder.toString());
            deviceList.setDeviceLocation("Bangalore");
            deviceList.setDeviceType("SCALANCE Z201");
            deviceList.setPhysicalAddress("84-C5-A6-8F-34-41");
            //System.out.println(deviceList);
            org.apache.kafka.clients.producer.KafkaProducer<String, DeviceList> producer3 =
                    new org.apache.kafka.clients.producer.KafkaProducer<>(properties);
            ProducerRecord<String, DeviceList> producerRecord =
                    new ProducerRecord<>("subscribeResponse1",deviceList);
            producer3.send(producerRecord);
        }
//    }
    }
}
//IO:- 1)To consume 2 ip ranges 2) To produce data between ip ranges
