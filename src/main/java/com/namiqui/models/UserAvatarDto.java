package com.namiqui.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class UserAvatarDto {

  private Long id;

  private String name;

  @JsonProperty("image_url")
  private String imageUrl;

  @JsonProperty("created_at")
  private LocalDateTime createdAt;
  
}
