package com.namiqui.models;

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
public class NamiUserDTO {
  
    private Long idUser;
    
    private String username;
    
    private String email;
    
    private String phone;
    
    private String name;
    
    private UserAvatarDto avatar;
}
