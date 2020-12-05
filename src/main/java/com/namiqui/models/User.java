package com.namiqui.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Entity
//@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable{
    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //@Column(unique = true, length = 60)
    private String email;

    //@Column(length = 120)
    private String password;

    private String name;

    //@Column(name = "fcm_token")
    private String fcmToken;

    private String address;

    //@Column(length = 13)
    private String phone;

    //@Column(unique = true, length = 30)
    private String username;

    private Boolean enabled;

    //@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinColumn(name="user_id" )
    private List<NamiUser> namiusers = new ArrayList<>();

    //@OneToMany(fetch= FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinColumn(name="user_id" )
    private List<UserConfiguration> configurations = new ArrayList<>();

    //@ManyToMany(fetch = FetchType.LAZY)
    //@JoinTable(joinColumns = @JoinColumn(name="user_id"),
    //        inverseJoinColumns = @JoinColumn(name="role_id"),
    //        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})})
    private List<Role> roles = new ArrayList<>();

    //@Column(name="created_at", columnDefinition = "datetime")
    //@CreationTimestamp
    private LocalDateTime createdAt;

    //@ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name="user_image_predefined" )
    private UserImagePredefined userImage;

    //@Column(length = 500)
    private String userImageUrl;

    //@Column(name="address_state_id")
    private Integer stateId;

    //@Column(name="address_city", length = 100)
    private String city;

    //@Column(name="address_colony", length = 100)
    private String colony;

    //@Column(name="address_cp", length = 5)
    private String postalCode;

//    public User(UserDTO user){
//        this.email = user.getEmail();
//        this.address = user.getAddress();
//        this.phone = user.getPhone();
//        this.username = user.getUsername();
//        this.password = user.getPassword();
//        this.name = user.getName();
//        this.enabled = true;
//        this.fcmToken = user.getFcmToken();
//        this.userImageUrl = user.getUserImageUrl();
//        this.postalCode = user.getPostalCode();
//        this.stateId = user.getStateId();
//        this.city = user.getCity();
//        this.colony = user.getColony();
//    }
}
