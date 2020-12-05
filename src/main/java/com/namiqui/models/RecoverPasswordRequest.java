package com.namiqui.models;

import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecoverPasswordRequest {

  private Long userId;
  
  private String code;
  
}
