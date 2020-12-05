package com.namiqui.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressDto {

  private String address;
  
  @JsonProperty("address_state_id")
  private Long stateId;
  
  private Long countryId;
  
  @JsonProperty("address_cp")
  private String postalCode;
  
  @JsonProperty("address_city")
  private String city;
  
  @JsonProperty("address_colony")
  private String colony;

}
