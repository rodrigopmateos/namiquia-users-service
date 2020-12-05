package com.namiqui.respository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.namiqui.domain.UserAvatar;

public interface IUserImagePredefinedRepository extends JpaRepository<UserAvatar, Long> {

  Optional<UserAvatar> findById(Long id);

}
