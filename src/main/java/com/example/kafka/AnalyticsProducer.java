package com.example.kafka;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.javaapi.producer.ProducerData;
import kafka.producer.ProducerConfig;

public class AnalyticsProducer extends Thread {

	private final Producer<Integer, String> producer;
	private final String topic;
	private final Properties props = new Properties();

	public AnalyticsProducer() {
		this.props.put("serializer.class", "kafka.serializer.StringEncoder");
		this.props.put("zk.connect", "localhost:2181");
		// Use random partitioner. Don't need the key type. Just set it to Integer.
		// The message is of type String.
		this.producer = new Producer<Integer, String>(new ProducerConfig(props));
		this.topic = KafakTopics.COMPLETE_ANALYTICS.name();
	}

	public void run() {
		int messageNo = 1;
		while (true) {
			String messageStr = new String("Message_" + messageNo);
			producer.send(new ProducerData<Integer, String>(topic, messageStr));
			messageNo++;
		}
	}

}
