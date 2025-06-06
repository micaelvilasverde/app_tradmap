package componentes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    private KafkaTemplate<String, String> kafkaTemplate;
    private KafkaTemplate<String, Object> customKafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate,
                                  KafkaTemplate<String, Object> customKafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.customKafkaTemplate = customKafkaTemplate;
    }
    /*Configuração de enviar mensagem padrão*/
    public void sendMessage(String message, String topicName) {
        kafkaTemplate.send(topicName, message);
    }

    public void sendCustomMessage(Object message, String topicName) {
        customKafkaTemplate.send(topicName, message);
    }

}
