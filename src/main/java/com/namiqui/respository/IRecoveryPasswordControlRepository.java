package com.namiqui.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import com.namiqui.domain.RecoveryPassword;

public interface IRecoveryPasswordControlRepository extends JpaRepository<RecoveryPassword, Long> {

  RecoveryPassword findByIdUserIdAndCodeAndUpdated(@Param("userId") Long userId,
      @Param("code") String code, @Param("updated") Integer updated);

}
