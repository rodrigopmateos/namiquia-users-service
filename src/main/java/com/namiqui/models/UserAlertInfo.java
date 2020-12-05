package com.namiqui.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAlertInfo implements Serializable {
    @JsonProperty("user_id")
    private Long id;

    private String username;

    private String name;

    @JsonProperty("user_image_url")
    private String userImageUrl;

    private List<NamiuserAlertInfo> namiusers = new ArrayList<>();
}
