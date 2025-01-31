package fr.younes.consumer.kafka.consumerkafka.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Envoie un message dans un topic Kafka.
     *
     * @param topic   Le nom du topic Kafka
     * @param key     (Facultatif) La clé du message
     * @param message Le contenu du message
     */
    public void sendMessage(String topic, String key, String message) {
        if (key == null || key.isEmpty()) {
            kafkaTemplate.send(topic, message);
        } else {
            kafkaTemplate.send(topic, key, message);
        }
        System.out.printf("Message envoyé dans le topic %s : clé=%s, message=%s%n", topic, key, message);
    }
}
