package com.namiqui.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class UserUpdateResponse {

  private String username;

  private String address;

  private String name;

  private String phone;

  private List<NamiUserDTO> namiusers = new ArrayList<>();

  @JsonProperty("address_cp")
  private String postalCode;

  @JsonProperty("address_state_id")
  private Long stateId;

  @JsonProperty("address_city")
  private String city;

  @JsonProperty("address_colony")
  private String colony;

  @JsonProperty("user_image_default")
  private Long userImage;

  @JsonProperty("user_image_url")
  private String userImageUrl;

}
