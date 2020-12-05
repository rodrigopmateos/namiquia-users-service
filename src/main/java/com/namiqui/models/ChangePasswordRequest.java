package com.namiqui.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class ChangePasswordRequest implements Serializable{

    @NotEmpty(message = "Code is required")
    private String code;

    @NotEmpty(message = "Username is required")
    private String username;

    @NotEmpty(message = "Old password is required")
    private String oldPassword;

    @NotEmpty(message = "New password is required")
    private String newPassword;
}
