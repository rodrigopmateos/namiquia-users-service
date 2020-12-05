package com.namiqui.models;

import java.time.LocalDateTime;
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
public class ConfigurationDTO {

  private Long userId;
  
  private Integer alert911;
  
  private Integer alertUser;
  
  private LocalDateTime created;
}
