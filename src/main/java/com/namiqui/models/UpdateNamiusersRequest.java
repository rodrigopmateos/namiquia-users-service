package com.namiqui.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateNamiusersRequest implements Serializable{
    @NotEmpty(message = "username is required")
    private String username;
    @NotNull(message ="namiusers is required, minimum 2")
    private List<NamiUserRequest> namiusers;
}
