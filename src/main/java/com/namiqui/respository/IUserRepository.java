package com.namiqui.respository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.namiqui.domain.UserD;

@Repository
public interface IUserRepository extends JpaRepository<UserD, Long> {

	UserD findByUsername(@Param("username") String username);

	UserD findByEmail(@Param("email") String email);

    Integer countByEmail(String email);

    Integer countByUsername(String username);

    List<UserD> getAllByEnabled(Boolean enabled);

//    @Query(value = "SELECT u.id, u.name, u.address, u.created_at, u.email, u.enabled, u.fcm_token, u.password, " +
//            "u.phone, u.username, u.user_image_url, u.user_image_predefined, u.address_cp, u.address_state_id, u.address_city, " +
//            "u.address_colony FROM users u ", nativeQuery = true)
//    List<UserD> getAll();

}
