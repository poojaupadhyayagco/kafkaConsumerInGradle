package agco.debug;

import static agco.configuration.ServiceConstants.DEVICE_REF_TOPIC;

import agco.model.DeviceReferenceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/device-ref-data")
public class DeviceRefDataController {
  @Autowired
  private KafkaTemplate<String, DeviceReferenceData> deviceRefDataKafkaTemplate;

  @PostMapping(value = "/publish-event")
  public void sendMessageToKafkaTopic(@RequestParam("name") String name, @RequestParam("age") Integer age) {
    this.deviceRefDataKafkaTemplate.send(
        DEVICE_REF_TOPIC,
        String.valueOf(System.currentTimeMillis()),
        new DeviceReferenceData(name, age));
  }
}
