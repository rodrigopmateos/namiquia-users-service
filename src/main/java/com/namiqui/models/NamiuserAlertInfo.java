package com.namiqui.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NamiuserAlertInfo extends NamiUserRequest implements Serializable {
    private String name;
    @JsonProperty("fcm_token")
    private String fcmToken;
    private String email;
    private String phone;
    private UserAvatarDto avatar;
}
