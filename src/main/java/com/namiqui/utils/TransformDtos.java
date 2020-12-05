package com.namiqui.utils;

import java.time.LocalDateTime;
import java.util.Optional;
import com.namiqui.domain.Address;
import com.namiqui.domain.AddressId;
import com.namiqui.domain.UserAvatar;
import com.namiqui.domain.UserConfigurationD;
import com.namiqui.domain.UserConfigurationId;
import com.namiqui.domain.UserD;
import com.namiqui.domain.UserPackage;
import com.namiqui.models.AddressDto;
import com.namiqui.models.ConfigurationDTO;
import com.namiqui.models.RoleDTO;
import com.namiqui.models.UserAvatarDto;
import com.namiqui.models.UserDTO;

public class TransformDtos {

  public static UserD convertToUser(UserDTO user) {

    return UserD.builder().email(user.getEmail())
        .address(convertToAddress(Optional.ofNullable(user.getAddress()))).phone(user.getPhone())
        .username(user.getUsername()).password(user.getPassword()).name(user.getName()).enabled(1)
        .fcmToken(user.getFcmToken()).avatar(convertToAvatar(Optional.ofNullable(user.getAvatar())))
        .userPackage(convertToPackage(Optional.ofNullable(user.getRole())))
        .userConfiguration(convertToConfiguration(Optional.ofNullable(user.getUserConfiguration())))
        .build();
  }

  public static Address convertToAddress(Optional<AddressDto> dtoOptional) {
    if (dtoOptional.isPresent()) {
      AddressDto dto = dtoOptional.get();
      return Address.builder().address(dto.getAddress()).city(dto.getCity()).colony(dto.getColony())
          .countryId(dto.getCountryId()).postalCode(dto.getPostalCode()).stateId(dto.getStateId())
          .id(AddressId.builder().build()).build();
    }
    return null;
  }

  public static UserAvatar convertToAvatar(Optional<UserAvatarDto> dtoImageoptional) {

    if (dtoImageoptional.isPresent()) {
      UserAvatarDto dtoImage = dtoImageoptional.get();

      return UserAvatar.builder().name(dtoImage.getName()).imageUrl(dtoImage.getImageUrl())
          .created(LocalDateTime.now()).build();
    }
    return null;
  }

  public static UserPackage convertToPackage(Optional<RoleDTO> dtopackageOptional) {
    if (dtopackageOptional.isPresent()) {
      RoleDTO dtoAvatar = dtopackageOptional.get();
      return UserPackage.builder().name(dtoAvatar.getName()).id(dtoAvatar.getId()).enabled(1)
          .created(LocalDateTime.now()).build();
    }
    return null;
  }

  public static UserConfigurationD convertToConfiguration(
      Optional<ConfigurationDTO> dtoConfigOptional) {
    if (dtoConfigOptional.isPresent()) {
      ConfigurationDTO dtoConfig =  dtoConfigOptional.get();
      return UserConfigurationD.builder().alert911(dtoConfig.getAlert911())
          .alertUser(dtoConfig.getAlertUser()).created(dtoConfig.getCreated())
          .id(UserConfigurationId.builder().userId(dtoConfig.getUserId()).build()).build();
    }
    return null;
  }
}
