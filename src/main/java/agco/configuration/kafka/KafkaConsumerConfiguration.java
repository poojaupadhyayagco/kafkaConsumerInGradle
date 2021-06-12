package agco.configuration.kafka;

import java.util.HashMap;
import java.util.Map;

import agco.model.DeviceReferenceData;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.LoggingErrorHandler;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@EnableKafka
public class KafkaConsumerConfiguration {

  public String getKafkaSecuritySaslJaasConfig() {
    return kafkaSecuritySaslJaasConfig;
  }

  public void setKafkaSecuritySaslJaasConfig(String kafkaSecuritySaslJaasConfig) {
    this.kafkaSecuritySaslJaasConfig = kafkaSecuritySaslJaasConfig;
  }

  /**
   * Kafka security sasl jaas config.
   */
  @Value("${spring.kafka.properties.sasl.jaas.config}")
  private String kafkaSecuritySaslJaasConfig;


  @Bean
  @ConditionalOnMissingBean(ConsumerFactory.class)
  public ConsumerFactory<String, DeviceReferenceData> consumerFactory() {
    return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(),
        new JsonDeserializer<>(DeviceReferenceData.class, true));
  }


  /**
   * Build ContainerFactory for Telemetry DataFrame events on Kafka.
   *
   * @return Telemetry ConsumerFactory for Kafka
   */
  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, DeviceReferenceData>
  kafkaListenerContainerFactory() {

    ConcurrentKafkaListenerContainerFactory<String, DeviceReferenceData> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    return factory;

  }

  private Map<String, Object> consumerConfigs() {

    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "AET_DEV_REF_DATA_GID");
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
    props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
    props.put("sasl.jaas.config", getKafkaSecuritySaslJaasConfig());
    props.put("sasl.mechanism", "PLAIN");
    props.put("security.protocol", "PLAINTEXT");
    return props;
  }


}
