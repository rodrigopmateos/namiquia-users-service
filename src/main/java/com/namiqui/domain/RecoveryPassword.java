package com.namiqui.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
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
@Entity
@Table(name = "user_recovery_password")
@ToString
public class RecoveryPassword {

  @EmbeddedId
  private RecoveryPasswordId id;

  private String code;

  private Integer updated;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "valid_until")
  private LocalDateTime validAt;

  @Column(name = "created_at")
  private LocalDateTime created;

  @ManyToOne(fetch = FetchType.LAZY)
  @Cascade(CascadeType.ALL)
  @MapsId("userId")
  private UserD user;
}
