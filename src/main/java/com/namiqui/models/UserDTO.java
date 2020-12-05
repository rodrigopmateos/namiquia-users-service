package com.namiqui.models;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@ApiModel(description = "Modelo con detalles de un usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

  private Long id;

  @ApiModelProperty(notes = "El email es requerido")
  @NotEmpty(message = "Email is required")
  private String email;

  @ApiModelProperty(notes = "El password es requerido")
  @NotEmpty(message = "Password is required")
  private String password;

  @ApiModelProperty(notes = "El nombre es requerido")
  @NotEmpty(message = "Name is required")
  private String name;

  @ApiModelProperty(notes = "El token es requerido")
  @NotEmpty(message = "Token is required")
  private String fcmToken;

  private ConfigurationDTO userConfiguration;

  private AddressDto address;

  private Integer enabled;

  private RoleDTO role;

  @ApiModelProperty(notes = "Los namiusers son requeridos")
  @NotEmpty(message = "Namiusers is required, try with namiqui1 and namiqui2")
  private final List<NamiUserDTO> namiusers = new ArrayList<>();

  @ApiModelProperty(notes = "El telefono es requerido")
  @NotEmpty(message = "Phone is required")
  private String phone;

  @ApiModelProperty(notes = "El username es requerido")
  @NotEmpty(message = "Username is required")
  private String username;

  private UserAvatarDto avatar;

}