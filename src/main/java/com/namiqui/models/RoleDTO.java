package com.namiqui.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RoleDTO {
  private Long id;

  private String name;

  private Integer enabled;

  @JsonProperty("created_at")
  private String createdAt;

}
