package com.example.kafka;

public class KafkaProperties {

	final static String zkConnect = "127.0.0.1:2181";
	final static String groupId = "test-consumer-group";
	final static String kafkaServerURL = "localhost";
	final static int kafkaServerPort = 9092;
	final static int kafkaProducerBufferSize = 64 * 1024;
	final static int connectionTimeOut = 100000;
	final static int reconnectInterval = 10000;
}
