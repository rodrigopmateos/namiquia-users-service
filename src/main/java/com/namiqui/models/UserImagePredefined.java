package com.namiqui.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
//@Entity
//@Table(name = "user_images_predefined")
@AllArgsConstructor
@NoArgsConstructor
public class UserImagePredefined {

    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //@Column(length = 100)
    private String name;

    //@Column(length = 500)
    private String imageUrl;

    //@Column(name="created_at", columnDefinition = "datetime")
    //@CreationTimestamp
    private LocalDateTime createdAt;

    public UserImagePredefined(UserImagePredefinedDTO userImage) {
        this.id = userImage.getId();
        this.name = userImage.getName();
        this.imageUrl = userImage.getImageUrl();
    }
}
