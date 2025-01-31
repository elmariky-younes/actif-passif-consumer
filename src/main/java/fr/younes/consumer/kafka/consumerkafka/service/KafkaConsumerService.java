package fr.younes.consumer.kafka.consumerkafka.service;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Service
public class KafkaConsumerService {
private KafkaConsumer<String, String> consumer;
    private volatile boolean running = true; // Pour contrôler si le consumer doit continuer

    private KafkaConsumer<String, String> createConsumer() {
  
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:8094,localhost:8093,localhost:8092");
        props.put("group.id", "test-group");
        props.put("enable.auto.commit", "true");  // Kafka commit automatiquement les offsets
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("client.id", "client-active");
       // props.put("leave.group.on.close", "true");

        return new KafkaConsumer<>(props);
       // consumer.subscribe(Collections.singletonList("mon-topic3")); // S'abonne au topic
    }

    @PostConstruct
    void init() {
     //   if (autoStart) {
            startConsuming("mon-topic3");  // Démarrer automatiquement sur "m
    }
   // séparé
    public void startConsuming(String topic) {
        if(consumer!= null) {
            consumer.close(); // Ferme proprement l'ancien consumer
        }
        consumer = createConsumer(); // Crée une nouvelle instance
    running = true;

  
        new Thread(() -> {
            consumer.subscribe(Collections.singletonList(topic));
            System.out.println("Kafka Consumer started on topic: " + topic);

            try {
                while (running) {
                    var records = consumer.poll(Duration.ofMillis(100));
                    for (ConsumerRecord<String, String> record : records) {
                        System.out.printf("Consumed message: key=%s, value=%s, offset=%d, partition=%d%n",
                                record.key(), record.value(), record.offset(), record.partition());
                    }
                }
            } catch (WakeupException e) {
                System.out.println("Consumer wakeup called, stopping...");
            } finally {
                consumer.close();
                System.out.println("Kafka Consumer stopped.");
            }
        }).start();
    }

    // Arrêter le consumer proprement
    public void stop() {
        if (running) {
            running = false;
            System.out.println("Stopping Kafka Consumer...");
            consumer.wakeup();  // Interrompt immédiatement le `poll()`
        } else {
            System.out.println("Consumer already stopped.");
        }
    }

    @PreDestroy
    public void shutdown() {
        stop();
    }

}