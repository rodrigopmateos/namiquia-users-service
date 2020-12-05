package com.namiqui.controllers;

import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.namiqui.domain.UserD;
import com.namiqui.domain.UserPackage;
import com.namiqui.models.ErrorDTO;
import com.namiqui.models.ResponseGeneric;
import com.namiqui.models.RoleCreateRequest;
import com.namiqui.models.RoleUpdateRequest;
import com.namiqui.models.UserAvatarDto;
import com.namiqui.models.UserRegisterRequest;
import com.namiqui.services.IRoleService;
import com.namiqui.services.IUserImagePredefinedService;
import com.namiqui.services.IUsersService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private IUsersService usersService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserImagePredefinedService userImagePredefinedService;

    @ApiOperation(value="Metodo para actualizar información de un usuario especifico.", response = ResponseGeneric.class)
    @PutMapping(value = "/updateInfo", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserRegisterRequest  request) {
        ResponseGeneric response = new ResponseGeneric();
        response.setTimestamp(new Date());
        try {
            response.setData(usersService.updateInfoByAdmin(request));
            response.setCodeStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.setErrors(new ErrorDTO(ex.getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="Metodo para crear un usuario nuevo", response = ResponseGeneric.class)
    @PostMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterRequest request) {
        ResponseGeneric response = new ResponseGeneric();
        response.setTimestamp(new Date());
        try {
            response.setData(usersService.register(request));
            response.setCodeStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.setErrors(new ErrorDTO(ex.getMessage()));
            response.setCodeStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="Metodo para obtener el listado de usuarios.", response = ResponseGeneric.class)
    @GetMapping(value = "/users/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers() {
        ResponseGeneric response = new ResponseGeneric();
        response.setTimestamp(new Date());
        try {
            List<UserD> users = usersService.getAllUsers();
            response.setData(users);
            response.setCodeStatus(HttpStatus.OK.value());
            return new ResponseEntity<Object>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.setErrors(new ErrorDTO(ex.getMessage()));
            response.setCodeStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="Metodo para obtener el listado de roles.", response = ResponseGeneric.class)
    @GetMapping(value = "/role/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllRoles() {
        ResponseGeneric response = new ResponseGeneric();
        response.setTimestamp(new Date());
        try {
            List<UserPackage> roles = roleService.getAllRoles();
            response.setData(roles);
            response.setCodeStatus(HttpStatus.OK.value());
            return new ResponseEntity<Object>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.setErrors(new ErrorDTO(ex.getMessage()));
            response.setCodeStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="Metodo para crear un rol", response = ResponseGeneric.class)
    @PostMapping(value = "/role", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createRole(@Valid @RequestBody RoleCreateRequest request) {
        ResponseGeneric response = new ResponseGeneric();
        response.setTimestamp(new Date());
        try {
            response.setData(roleService.createRole(request));
            response.setCodeStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.setErrors(new ErrorDTO(ex.getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="Metodo para actualizar un rol", response = ResponseGeneric.class)
    @PutMapping(value = "/role", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createRole(@Validated @RequestBody RoleUpdateRequest request) {
        ResponseGeneric response = new ResponseGeneric();
        response.setTimestamp(new Date());
        try {
            response.setData(roleService.updateRole(request));
            response.setCodeStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.setErrors(new ErrorDTO(ex.getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="Metodo para elimina run rol", response = ResponseGeneric.class)
    @DeleteMapping(value = "/role", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createRole(@RequestParam(name = "id") Long id) {
        ResponseGeneric response = new ResponseGeneric();
        response.setTimestamp(new Date());
        try {
            response.setData(roleService.deleteRole(id));
            response.setCodeStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.setErrors(new ErrorDTO(ex.getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="Metodo para obtenerlas el catalogo de las imágenes por default para los usuarios.", response = ResponseGeneric.class)
    @GetMapping(value = "/user-images-catalog/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserImagesCatalog() {
        ResponseGeneric response = new ResponseGeneric();
        response.setTimestamp(new Date());
        try {
            response.setData(userImagePredefinedService.getUserImagesAll());
            response.setCodeStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.setErrors(new ErrorDTO(ex.getMessage()));
            response.setCodeStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="Metodo para guardar un item para el catalogo de imagenes predefinidas de usuarios.", response = ResponseGeneric.class)
    @PostMapping(value = "/user-images-catalog", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveUserImageItem(@Valid @RequestBody UserAvatarDto request) {
        ResponseGeneric response = new ResponseGeneric();
        response.setTimestamp(new Date());
        try {
            response.setData(userImagePredefinedService.saveUserImagePredefined(request));
            response.setCodeStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.setErrors(new ErrorDTO(ex.getMessage()));
            response.setCodeStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
