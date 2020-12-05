package com.namiqui.utils;

import java.util.Optional;
import com.namiqui.domain.Address;
import com.namiqui.domain.UserAvatar;
import com.namiqui.domain.UserConfigurationD;
import com.namiqui.domain.UserD;
import com.namiqui.models.AddressDto;
import com.namiqui.models.ConfigurationDTO;
import com.namiqui.models.NamiUserDTO;
import com.namiqui.models.UserAvatarDto;
import com.namiqui.models.UserDTO;
import com.namiqui.models.UserUpdateResponse;

public class TransformEntity {

  public static UserAvatarDto convertToAvatar(Optional<UserAvatar> dtoImageOptional) {
    if (dtoImageOptional.isPresent()) {
      UserAvatar dtoImage = dtoImageOptional.get();
      return UserAvatarDto.builder().name(dtoImage.getName()).imageUrl(dtoImage.getImageUrl())
          .createdAt(dtoImage.getCreated()).id(dtoImage.getId().longValue()).build();
    }
    return null;
  }

  public static UserUpdateResponse conrtToUserResponse(UserD user) {
    return UserUpdateResponse.builder().address(user.getAddress().getAddress()).name(user.getName())
        .username(user.getUsername()).phone(user.getPhone())
        .postalCode(user.getAddress().getPostalCode()).stateId(user.getAddress().getStateId())
        .city(user.getAddress().getCity()).colony(user.getAddress().getColony())
        .userImage(user.getAvatar().getId().longValue())
        .userImageUrl(user.getAvatar().getImageUrl()).build();
  }

  public static ConfigurationDTO convertToConfiguration(
      Optional<UserConfigurationD> configOptional) {
    if (configOptional.isPresent()) {
      UserConfigurationD config = configOptional.get();
      return ConfigurationDTO.builder().alert911(config.getAlert911())
          .alertUser(config.getAlertUser()).userId(config.getId().userId)
          .created(config.getCreated()).build();
    }
    return null;
  }

  public static UserDTO convertToUser(UserD userToConvert) {
    return UserDTO.builder().id(userToConvert.getUserId()).email(userToConvert.getEmail())
        .address(convertoAddress(Optional.ofNullable(userToConvert.getAddress())))
        .phone(userToConvert.getPhone()).username(userToConvert.getUsername())
        .password(userToConvert.getPassword()).name(userToConvert.getName())
        .fcmToken(userToConvert.getFcmToken())
        .avatar(convertToAvatar(Optional.ofNullable(userToConvert.getAvatar())))
        .userConfiguration(
            convertToConfiguration(Optional.ofNullable(userToConvert.getUserConfiguration())))
        .enabled(userToConvert.getEnabled()).build();
  }

  public static NamiUserDTO convertToNamiuser(UserD userToConvert) {
    return NamiUserDTO.builder().idUser(userToConvert.getUserId()).email(userToConvert.getEmail())
        .phone(userToConvert.getPhone()).username(userToConvert.getUsername())
        .name(userToConvert.getName())
        .avatar(convertToAvatar(Optional.ofNullable(userToConvert.getAvatar()))).build();
  }

  public static AddressDto convertoAddress(Optional<Address> addressOptional) {
    if (addressOptional.isPresent()) {
      Address address = addressOptional.get();
      return AddressDto.builder().address(address.getAddress()).city(address.getCity())
          .colony(address.getColony()).countryId(address.getCountryId())
          .postalCode(address.getPostalCode()).stateId(address.getStateId()).build();
    }
    return null;
  }

}
