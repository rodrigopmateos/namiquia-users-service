package com.namiqui.models;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
@ToString
public class UserUpgradeRequest {
  
  @NotNull(message = "Username is required")
  private String username;

  @NotNull(message = "Membership is required, [USER_FREE, USER_PRO, USER_PREMIUM]")
  private Long membership;

}
