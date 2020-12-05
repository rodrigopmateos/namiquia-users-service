package com.namiqui.models;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

    @NotEmpty(message = "Username is required")
    private String username;

    private AddressDto address;

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Phone is required")
    private String phone;

    private Long userImage;

    //@JsonProperty("user_image_url")
    //private String userImageUrl;

}
