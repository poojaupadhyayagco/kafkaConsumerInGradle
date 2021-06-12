package agco.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceReferenceData {
  @JsonProperty
  public String name;

  @JsonProperty
  public Integer age;


  public DeviceReferenceData() {
  }

  public DeviceReferenceData(String name, Integer age) {
    this.name = name;
    this.age = age;
  }
}
