package com.namiqui.services;

import java.util.List;
import com.namiqui.domain.UserPackage;
import com.namiqui.models.RoleCreateRequest;
import com.namiqui.models.RoleDTO;
import com.namiqui.models.RoleUpdateRequest;

public interface IRoleService {

    RoleDTO createRole(RoleCreateRequest r) throws Exception;

    RoleDTO updateRole(RoleUpdateRequest r) throws Exception;

    Long deleteRole(Long id) throws Exception;

    List<UserPackage> getAllRoles() throws Exception;

    UserPackage getOneRole(Long id) throws Exception;
}
