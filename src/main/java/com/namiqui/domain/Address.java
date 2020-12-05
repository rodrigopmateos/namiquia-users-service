package com.namiqui.domain;

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

/**
 * Clase que representa la entidad user_address.
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
@Table(name = "user_address")
@ToString
public class Address {


  @EmbeddedId
  private AddressId id;
  /**
   * Variable que representa el campo id.
   */


  private String address;

  @Column(name = "app_state_id")
  private Long stateId;

  @Column(name = "app_country_id")
  private Long countryId;

  @Column(name = "postal_code")
  private String postalCode;

  @Column(name = "city")
  private String city;

  @Column(name = "colony")
  private String colony;

  @OneToOne
  @Cascade(CascadeType.ALL)
  @MapsId("userId")
  private UserD user;

}
