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
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
//@Entity
//@Table(name = "nami_users")
@NoArgsConstructor
@AllArgsConstructor
public class NamiUser implements Serializable {
    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //@Column(length = 30)
    private String username;

    //@CreationTimestamp
    private LocalDateTime created_at;

    public NamiUser(Long id, String username){
        this.id = id;
        this.username = username;
    }
}
