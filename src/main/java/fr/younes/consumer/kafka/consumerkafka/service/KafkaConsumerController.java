package fr.younes.consumer.kafka.consumerkafka.service;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaConsumerController {

    private final KafkaConsumerService consumerService;

    public KafkaConsumerController(KafkaConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @PostMapping("/stop")
    public String stopConsumer() {
        consumerService.stop();
        return "Consumer stopped successfully.";
    }    
    
    @PostMapping("/start")
    public String startConsumer() {
        consumerService.startConsuming("mon-topic3");
        return "Consumer start successfully.";
    }  
}