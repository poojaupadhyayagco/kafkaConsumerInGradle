package agco.configuration.kafka;

import agco.model.DeviceReferenceData;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@Configuration
public class KafkaProducerConfiguration {
  @Autowired
  private KafkaProperties kafkaProperties;

  @Bean
  public ProducerFactory<String, DeviceReferenceData> producerFactory() {
    Map<String, Object> properties = kafkaProperties.buildProducerProperties();
    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    return new DefaultKafkaProducerFactory<>(properties);
  }

  @Bean
  public KafkaTemplate<String, DeviceReferenceData> deviceRefDataKafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }
}
