package com.sinec.kafkaproducer;

import com.sinec.kafkaproducer.consumer.KafkaProcessor;
import com.sinec.kafkaproducer.entity.DeviceList;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Properties;

@SpringBootApplication
public class KafkaIOApplication {

	//private static final Logger log = (Logger) LoggerFactory.getLogger(KafkaproducerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(KafkaIOApplication.class, args);
		KafkaProcessor processor = new KafkaProcessor();
		try {
			processor.init();
		} catch (Exception exp) {
			processor.shutdown();
		}
	}
	//producer code
}


//	@Bean
//	CommandLineRunner commandLineRunner()
//	{
//		return args -> {
//
//			DeviceList deviceList=new DeviceList();
//			String ip="192.168.1.2";
//			Properties properties = new Properties();
//			properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
//			properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//			properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
//
//			// create the producer
//			KafkaProducer<String,String> producer=new KafkaProducer<>(properties);
//
//			KafkaProducer<String ,Boolean> producer2=new KafkaProducer<>(properties);
//				for (int i = 1; i <20; i++) {
//					if (i < 501) {
//						StringBuilder builder = new StringBuilder("192.168.1.");
////						String newchar = Integer.toString(i);
////						builder.append(newchar);
////						deviceList.setId(Long.parseLong(Integer.toString(i)));
////						deviceList.setIpAddress(builder.toString());
////						deviceList.setDeviceLocation("Bangalore");
////						deviceList.setDeviceType("SCALANCE Z201");
////						deviceList.setPhysicalAddress("84-C5-A6-8F-34-41");
//						//System.out.println(deviceList);
//						ProducerRecord<String, String> producerRecord =
//								new ProducerRecord<>("command", "192.168.1.2");
//					    producer.send(producerRecord, new Callback() {
//							@Override
//							public void onCompletion(RecordMetadata recordMetadata, Exception e) {
//								if (e == null) {
//									// the record was successfully sent
//									ProducerRecord<String, Boolean> producerRecord1 =
//											new ProducerRecord<>("commandresponse", true);
//									producer2.send(producerRecord1);
//								} else {
//									ProducerRecord<String, Boolean> producerRecord1 =
//											new ProducerRecord<>("commandresponse", false);
//									producer2.send(producerRecord1);
//								}
//							}
//						});
//
//
//		}}
//
//
//
//		};}}
