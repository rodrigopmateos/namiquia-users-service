package com.namiqui.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class UserRegisterRequest implements Serializable{
    private static final long serialVersionUID = 1L;

    @Valid
    private UserDTO user;
}
