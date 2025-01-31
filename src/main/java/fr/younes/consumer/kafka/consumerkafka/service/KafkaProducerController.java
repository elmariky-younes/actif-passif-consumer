package fr.younes.consumer.kafka.consumerkafka.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka")
public class KafkaProducerController {

    private final KafkaProducerService kafkaProducerService;

    public KafkaProducerController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    /**
     * API pour produire un message dans Kafka.
     *
     * @param topic   Nom du topic Kafka
     * @param key     Clé du message (optionnel)
     * @param message Contenu du message
     * @return Réponse HTTP
     */
    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(
            @RequestParam String topic,
            @RequestParam String message) {

        kafkaProducerService.sendMessage(topic,null, message);
        return ResponseEntity.ok("Message envoyé au topic : " + topic);
    }
}
