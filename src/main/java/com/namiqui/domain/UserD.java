package com.namiqui.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * Clase entidad para la tabla user.
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
@Table(name = "users")
@ToString
public class UserD {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  public Long userId;

  @OneToOne
  @JoinColumn(name = "app_user_avatar_id")
  public UserAvatar avatar;

  @OneToOne
  @JoinColumn(name = "app_user_package_id")
  public UserPackage userPackage;

  public String name;

  public String email;

  public Integer enabled;

  @Column(name = "fcm_token")
  public String fcmToken;

  public String password;

  public String phone;

  @Column(name = "created_at")
  public LocalDateTime created;

  public String username;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
  private UserConfigurationD userConfiguration;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
  private Address address;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
  private final List<RecoveryPassword> recoveryPasswords = new ArrayList<>();

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user",
      orphanRemoval = true)
  private final List<NamiquiUser> namiquiUsers = new ArrayList<>();
}
