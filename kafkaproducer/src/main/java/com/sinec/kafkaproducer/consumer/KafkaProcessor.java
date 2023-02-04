package com.sinec.kafkaproducer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinec.kafkaproducer.entity.DeviceList;
import com.sinec.kafkaproducer.kafka.KafkaProducer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.support.ExecutorServiceAdapter;

import java.util.*;
import java.util.concurrent.*;

public class KafkaProcessor {


    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProcessor.class);
    private static final String KEY="DEVICE";
    String ip1;
    String ip2;


    //@KafkaListener(topicPartitions = {@TopicPartition(topic = "${kafka.topic.orders}", partitions = "0")})
    private final KafkaConsumer<String, String> myConsumer;
    private ExecutorService consumerexecutor=Executors.newSingleThreadExecutor();
    private ExecutorService producerexecutor=Executors.newSingleThreadExecutor();
    private static final Properties KAFKA_PROPERTIES = new Properties();
    static {
        KAFKA_PROPERTIES.put("bootstrap.servers", "localhost:9092");
        KAFKA_PROPERTIES.put("group.id", "myGroup");
        KAFKA_PROPERTIES.put("enable.auto.commit", "true");
        KAFKA_PROPERTIES.put("auto.commit.interval.ms", "1000");
        KAFKA_PROPERTIES.put("session.timeout.ms", "30000");
        KAFKA_PROPERTIES.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KAFKA_PROPERTIES.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    }


    TopicPartition partitionToReadFrom=new TopicPartition("command",0);


    public KafkaProcessor() {
        this.myConsumer = new KafkaConsumer<>(KAFKA_PROPERTIES);
        this.myConsumer.subscribe(Arrays.asList("cmd"));
        //this.myConsumer.assign(Arrays.asList(partitionToReadFrom));
        //this.myConsumer.seek(0,);
        // this.myConsumer.s
    }

    private static Queue<String> ipqueue=new ArrayBlockingQueue<>(2);
    //@KafkaListener(topicPartitions = {@TopicPartition(topic = "sinec", partitions = "0")})
    public void init() throws ExecutionException, InterruptedException, JsonProcessingException {

        // KafkaRecordHandler kafkaRecordHandler = new KafkaRecordHandler();
        // Queue<ConsumerRecord<String, String>> queue = new ArrayBlockingQueue<>(100);
        List<String> iprange=new ArrayList<>();
        while (true) {
            CompletableFuture<ConsumerRecords<String, String>> records1 =
                    CompletableFuture.supplyAsync(() -> myConsumer.poll(10000),consumerexecutor);
            for (ConsumerRecord<String, String> record : records1.get()) {
                LOGGER.info("Key: " + record.key() + ", Value: " + record.value());
                LOGGER.info("Partition: " + record.partition() + ", Offset:" + record.offset());
                KafkaProducer kafkaProducer=new KafkaProducer();
                ipqueue.add(record.value());
                if(ipqueue.size()==2)
                {
                   ip1=ipqueue.poll();
                   ip2=ipqueue.poll();
                   CompletableFuture.runAsync(()->
                   {
                       kafkaProducer.produceData(ip1,ip2);
                   },producerexecutor);

                }

               }

            }
        }




    public void shutdown() {
        if (myConsumer != null) {
            myConsumer.close();
        }
        if (consumerexecutor != null) {
            consumerexecutor.shutdown();
        }
        try {
            if (consumerexecutor != null && !consumerexecutor.awaitTermination(60, TimeUnit.MILLISECONDS)) {
                consumerexecutor.shutdownNow();
            }
        }catch (InterruptedException e) {
            consumerexecutor.shutdownNow();
        }
    }
}
