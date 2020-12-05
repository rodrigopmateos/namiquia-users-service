package com.namiqui.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Clase
 * 
 * @author OGUM.
 *
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "user_namiquiusers")
@ToString
public class NamiquiUser {

  /**
   * Identificador que hace referencia a los usuarios relacinados a la tabla User.
   */
  @EmbeddedId
  private NamiquiUserId id;

  @Column(name = "created_at")
  private LocalDateTime created;


  @ManyToOne(fetch = FetchType.LAZY)

  @MapsId("userId")
  private UserD user;
}
