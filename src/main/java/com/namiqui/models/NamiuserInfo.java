package com.namiqui.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NamiuserInfo implements Serializable {
    private Long id;

    private String username;

    private String token;

    private String email;

    private String phone;
}
