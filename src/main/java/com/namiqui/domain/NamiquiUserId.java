package com.namiqui.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Clase entidad que representa la llave compuesta.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Embeddable
@ToString
public class NamiquiUserId implements Serializable {

  /**
   * Serial Id.
   */
  private static final long serialVersionUID = 1L;

  /*
   * Identificador unico de tabla.
   */
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /*
   * Identificador que hace referencia a la tabla User.
   */
  @Column(name = "user_id")
  private Long userId;

  /**
   * Identificador que hace referencia a los usuarios relacinados a la tabla User.
   */
  @Column(name = "user_namiqui_id")
  private Long userNamiquiId;
}
