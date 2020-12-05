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
//@Table(name="roles")
@NoArgsConstructor
@AllArgsConstructor
public class Role  {
    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //@Column(unique = true, length = 30)
    private String name;

    private Boolean enabled;

    //@Column(name="created_at", columnDefinition = "datetime")
    //@CreationTimestamp
    private LocalDateTime createdAt;

    public Role(Long id, String name){
        this.id = id;
        this.name = name;
    }
}
