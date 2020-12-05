package com.namiqui.models;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class UserImagePredefinedDTO {
    private Long id;

    private String name;

    private String imageUrl;

    private LocalDateTime createdAt;
}
