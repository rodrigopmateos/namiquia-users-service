package com.namiqui.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Clase id para la tabla app_states.
 * @author OGUM.
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
@ToString
public class StateId implements Serializable {

	
	/**
	 * Id serial.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Llave compuesta.
	 */
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * Llave compuesta.
	 */
	@Column(name = "app_country_id")
	private Long countryId;
}
