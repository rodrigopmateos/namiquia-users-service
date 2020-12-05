package com.namiqui.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
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
@Table(name = "user_configuration")
@ToString
public class UserConfigurationD {

  /**
   * 
   */
  @EmbeddedId
  private UserConfigurationId id;

  @Column(name = "alert_action_call_911")
  private Integer alert911;

  @Column(name = "alert_action_alert_users")
  private Integer alertUser;

  @Column(name = "created_at")
  private LocalDateTime created;


  @OneToOne
  @Cascade(CascadeType.ALL)
  @MapsId("userId")
  private UserD user;
}
