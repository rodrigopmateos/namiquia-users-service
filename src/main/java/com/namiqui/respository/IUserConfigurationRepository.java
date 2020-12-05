package com.namiqui.respository;

import com.namiqui.domain.UserConfigurationD;
import com.namiqui.models.UserConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUserConfigurationRepository extends JpaRepository<UserConfigurationD, Long>{

    @Query(value = "SELECT uc.id, uc.key_name, uc.key_value, uc.type_value, uc.user_id, uc.created_at " +
            " FROM user_configuration uc WHERE uc.user_id = ?1 AND uc.key_name = ?2", nativeQuery = true)
    UserConfiguration findByUsernameAndKeyName(Long userId, String keyName);
}
