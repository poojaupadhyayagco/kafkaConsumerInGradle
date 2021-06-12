package agco.services.listener;

import agco.configuration.ServiceConstants;
import agco.model.DeviceReferenceData;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class DeviceReferenceDataConsumer {

  @KafkaListener(
      topics = {ServiceConstants.DEVICE_REF_TOPIC},
      clientIdPrefix = "telemetry-topics",
      containerFactory = "kafkaListenerContainerFactory"
  )
  public void listen(
      ConsumerRecord<String, DeviceReferenceData> cr, @Payload DeviceReferenceData payload) {
    System.out.printf("Message received: %s", payload);

  }
}
