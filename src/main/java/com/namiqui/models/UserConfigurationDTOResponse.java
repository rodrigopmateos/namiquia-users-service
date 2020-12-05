package com.namiqui.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class UserConfigurationDTOResponse {
    private String username;

    ConfigurationDTO configurations;
}
    
