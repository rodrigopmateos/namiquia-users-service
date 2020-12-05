package com.namiqui.utils;

import java.time.LocalDateTime;
import com.namiqui.domain.UserPackage;
import com.namiqui.models.RoleDTO;

public class RoleUtility {

  public static UserPackage toEntity(RoleDTO dtoPackage) {
    return UserPackage.builder().name(dtoPackage.getName()).enabled(dtoPackage.getEnabled())
        .id(dtoPackage.getId()).created(LocalDateTime.now()).build();
  }

  public static RoleDTO toDTO(UserPackage userPackage) {
    return RoleDTO.builder().name(userPackage.getName()).enabled(userPackage.getEnabled())
        .id(userPackage.getId()).createdAt(userPackage.getCreated().toString()).build();
  }
}
