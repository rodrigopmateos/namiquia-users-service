package com.namiqui.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Catalogo de configuraciones default.
 * @author OGUM
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="configuration_catalog")
public class ConfigurationCatalog {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="key_name")
    private String keyName;

    @Column(name="key_value", columnDefinition="TEXT")
    private String keyValue;

    @Column(name="created_at")
    @CreationTimestamp
    private LocalDate createdAt;
}
