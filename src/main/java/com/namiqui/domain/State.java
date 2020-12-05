package com.namiqui.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Clase entidad para la tabla app_states.
 * @author OGUM.
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "app_states")
@ToString
public class State {

	@EmbeddedId
	private StateId idState;
	
	private String name;
	
}
