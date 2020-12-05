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

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Embeddable
@ToString
public class RecoveryPasswordId implements Serializable {

  /**
   * Id de serial.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Variable que representa el campo id.
   */
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;

  /**
   * Variable que representa el campo user_id.
   */
  @Column(name = "user_id")
  public Long userId;
}
