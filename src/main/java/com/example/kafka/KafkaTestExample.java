package com.example.kafka;

public class KafkaTestExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		AnalyticsProducer producerThread = new AnalyticsProducer();
//	    producerThread.start();
	    
	    try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	    AnalyticsConsumer consumerThread = new AnalyticsConsumer();
	    consumerThread.start();
		
	}

}
