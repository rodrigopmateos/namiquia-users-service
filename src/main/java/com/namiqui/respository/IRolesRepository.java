package com.namiqui.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.namiqui.domain.UserPackage;

public interface IRolesRepository extends JpaRepository<UserPackage, Long> {

  UserPackage findByName(String name);
}
