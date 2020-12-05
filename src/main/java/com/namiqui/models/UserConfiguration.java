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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity
//@Table(name="user_configuration")
public class UserConfiguration implements Serializable {
    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //@Column(name="key_name")
    private String keyName;

    //@Column(name="key_value", columnDefinition="TEXT")
    private String keyValue;

    //@ManyToOne
    //@JoinColumn(name="type_value", referencedColumnName = "id")
    private DataType typeValue;

    //@Column(name="user_id")
    private Long userId;

    //@Column(name="created_at")
    //@CreationTimestamp
    private LocalDateTime createdAt;

}
