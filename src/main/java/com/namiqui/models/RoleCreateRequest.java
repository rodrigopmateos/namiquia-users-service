package com.namiqui.models;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleCreateRequest {
  @NotNull(message = "Name is required")
  private String name;

  @NotNull(message = "Enabled is required")
  private Integer enabled;
}
