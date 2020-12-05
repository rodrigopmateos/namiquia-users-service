package com.namiqui.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.namiqui.domain.UserPackage;
import com.namiqui.models.RoleCreateRequest;
import com.namiqui.models.RoleDTO;
import com.namiqui.models.RoleUpdateRequest;
import com.namiqui.respository.IRolesRepository;
import com.namiqui.utils.RoleUtility;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RolesService implements IRoleService {

  @Autowired
  private IRolesRepository rolesRepository;


  @Override
  public RoleDTO createRole(RoleCreateRequest r) throws Exception {
    log.info("Inicio creacion de rol...");
    try {
      UserPackage role_to_create = rolesRepository.findByName(r.getName());
      if (role_to_create == null) {
        role_to_create = rolesRepository.save(RoleUtility
            .toEntity(RoleDTO.builder().name(r.getName()).enabled(r.getEnabled()).build()));
        log.info("Role creado...");
        return RoleUtility.toDTO(role_to_create);
      } else {
        log.error("Objeto Rol existente");
        throw new Exception("Objeto Rol existente");
      }
    } catch (Exception ex) {
      log.error(ex.getMessage());
      throw new Exception(ex.getMessage());
    }
  }

  @Override
  public RoleDTO updateRole(RoleUpdateRequest r) throws Exception {
    log.info("Inicia actualizacion de rol...");
    try {
      UserPackage role_to_update = rolesRepository.getOne(r.getId());
      if (role_to_update != null) {
        role_to_update.setName(r.getName());
        role_to_update.setEnabled(r.getEnabled());
        role_to_update = rolesRepository.save(role_to_update);
        log.info("Rol actualizado...");
        return RoleUtility.toDTO(role_to_update);
      } else {
        log.error("Objeto Rol no entontrado");
        throw new Exception("Objeto Rol no entontrado");
      }
    } catch (Exception ex) {
      log.error(ex.getMessage());
      throw new Exception(ex.getMessage());
    }
  }

  @Override
  public Long deleteRole(Long id) throws Exception {
    log.info("Inicia borrado de Objeto Rol: {}...", id);
    try {
      UserPackage role_to_delete = rolesRepository.getOne(id);
      if (role_to_delete != null) {
        role_to_delete.setEnabled(0);
        rolesRepository.save(role_to_delete);
        log.info("Finaliza borrado de objeto Rol...");
        return id;
      } else {
        log.error("Objeto Rol no entontrado");
        throw new Exception("Objeto Rol no entontrado");
      }
    } catch (Exception ex) {
      log.error(ex.getMessage());
      throw new Exception(ex.getMessage());
    }
  }


  @Override
  public List<UserPackage> getAllRoles() throws Exception {
    return this.rolesRepository.findAll();
  }

  @Override
  public UserPackage getOneRole(Long id) throws Exception {
    return this.rolesRepository.getOne(id);
  }
}
