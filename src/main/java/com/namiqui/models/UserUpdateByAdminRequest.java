package com.namiqui.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateByAdminRequest extends UserUpdateRequest implements Serializable {
    private Boolean enabled;
}
