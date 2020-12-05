package com.namiqui.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class UserConfigurationDTORequest {
  
  private String username;

  ConfigurationDTO configurations;
}
