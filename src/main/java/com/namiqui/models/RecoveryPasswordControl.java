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
//@Table(name="recovery_password_control")
@NoArgsConstructor
@AllArgsConstructor
public class RecoveryPasswordControl {

    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //@Column(length = 30)
    private String username;

    //@Column(length = 6)
    private String code;

    private Boolean updated;

    //@Column(name="created_at")
    //@CreationTimestamp
    private LocalDateTime createdAt;

    //@Column(name="valid_until")
    private LocalDateTime validUntil;

    //@Column(name="updated_at")
    private LocalDateTime updatedAt;
}
