package com.namiqui.controllers;

import java.time.LocalDateTime;
import java.util.Date;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.namiqui.models.ChangePasswordRequest;
import com.namiqui.models.ErrorDTO;
import com.namiqui.models.RecoverPasswordRequest;
import com.namiqui.models.ResponseGeneric;
import com.namiqui.models.UpdateNamiusersRequest;
import com.namiqui.models.UpdateTokenRequest;
import com.namiqui.models.User;
import com.namiqui.models.UserAlertInfo;
import com.namiqui.models.UserConfigurationDTORequest;
import com.namiqui.models.UserDTO;
import com.namiqui.models.UserRegisterRequest;
import com.namiqui.models.UserUpdateRequest;
import com.namiqui.models.UserUpgradeRequest;
import com.namiqui.services.IUsersService;
import io.swagger.annotations.ApiOperation;

@RestController
public class UserController {

    @Autowired
    private IUsersService usersService;

    @ApiOperation(value="Metodo hello world, regresa un string", response = String.class)
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World";
    }

    @ApiOperation(value="Metodo para registrar un usuario nuevo", response = ResponseGeneric.class)
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterRequest request) {
        ResponseGeneric response = new ResponseGeneric();
        response.setTimestamp(new Date());
        try {
        	request.getUser().getUserConfiguration().setCreated(LocalDateTime.now());
            response.setData(usersService.register(request));
            response.setCodeStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setErrors(new ErrorDTO(ex.getMessage()));
            response.setCodeStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="Metodo para recuperar el password, enviando por email pasword temporal", response = ResponseGeneric.class)
    @PostMapping(value = "/recovery", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> recovery(@Valid @RequestBody RecoverPasswordRequest request) {
        ResponseGeneric response = new ResponseGeneric();
        response.setTimestamp(new Date());
        try {
            response.setData(usersService.recovery(request));
            response.setCodeStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.setErrors(new ErrorDTO(ex.getMessage()));
            response.setCodeStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="Metodo para recuperar el password, enviando por email pasword temporal", response = ResponseGeneric.class)
    @PostMapping(value = "/recoveryByCode", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> recoveryByCode(@Valid @RequestBody RecoverPasswordRequest request) {
        ResponseGeneric response = new ResponseGeneric();
        response.setTimestamp(new Date());
        try {
            response.setData(usersService.recoveryByCode(request));
            response.setCodeStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.setErrors(new ErrorDTO(ex.getMessage()));
            response.setCodeStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="Metodo para cambiar el password, es necesario enviar el password anterior", response = ResponseGeneric.class)
    @PostMapping(value = "/changePassword", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        ResponseGeneric response = new ResponseGeneric();
        response.setTimestamp(new Date());
        try {
            response.setData(usersService.changePassword(request));
            response.setCodeStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.setErrors(new ErrorDTO(ex.getMessage()));
            response.setCodeStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="Metodo para cambiar el password, por codigo.", response = ResponseGeneric.class)
    @PostMapping(value = "/changePasswordByCode", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePasswordByCode(@Valid @RequestBody ChangePasswordRequest request) {
        ResponseGeneric response = new ResponseGeneric();
        response.setTimestamp(new Date());
        try {
            response.setData(usersService.changePasswordByCode(request));
            response.setCodeStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.setErrors(new ErrorDTO(ex.getMessage()));
            response.setCodeStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="Metodo para actualizar información de un usuario.", response = ResponseGeneric.class)
    @PutMapping(value = "/updateInfo", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserUpdateRequest request) {
        ResponseGeneric response = new ResponseGeneric();
        response.setTimestamp(new Date());
        try {
            response.setData(usersService.updateInfo(request));
            response.setCodeStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.setErrors(new ErrorDTO(ex.getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="Metodo para actualizar el tipo de membresia de un usuario", response = ResponseGeneric.class)
    @PutMapping(value = "/upgrade", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity upgradeUser(@Valid @RequestBody UserUpgradeRequest request) {
        ResponseGeneric response = new ResponseGeneric();
        response.setTimestamp(new Date());
        try {
            response.setData(this.usersService.upgrade(request));
            response.setCodeStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.setErrors(new ErrorDTO(ex.getMessage()));
            response.setCodeStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Metodo para obtener el usuario, de uso exclusivo para el OAUTH2
     *
     * @param username
     * @return
     */
    @ApiOperation(value="Metodo para buscar usuario por username exclusivo para oauth", response = User.class)
    @GetMapping("/search/byUsername")
    public ResponseEntity<?> searchByUsername(@RequestParam(name = "username") String username) {
        UserDTO user = usersService.findByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
    /**
     * Metodo para obtener el usuario, de uso exclusivo para el OAUTH2
     *
     * @param username
     * @return
     */
    @ApiOperation(value="Metodo para buscar usuario por username exclusivo para oauth", response = User.class)
    @GetMapping("/search/byId")
    public ResponseEntity<?> searchById(@RequestParam(name = "id") Long id) {
        UserDTO user = usersService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @ApiOperation(value="Metodo para obtener namiusers de un usuario.", response = UserAlertInfo.class)
    @GetMapping(value = "/namiusers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNamiusers(@RequestParam(name = "username") String username) {
        UserAlertInfo info = usersService.getNamiusers(username);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    @ApiOperation(value="Metodo para obtener namiusers de un usuario.", response = UserAlertInfo.class)
    @GetMapping(value = "/namiusersById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNamiusersById(@RequestParam(name = "id") Long id) {
        UserAlertInfo info = usersService.getNamiusersById(id);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    /**
     * Metodo para determinar si el usuario existe.
     *
     * @param username
     * @return
     */
    @ApiOperation(value="Metodo para revisar si un usuario existe o no.", response = ResponseGeneric.class)
    @GetMapping(value = "/existUsername", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> existUsername(@RequestParam(name = "username") String username) {
        ResponseGeneric response = new ResponseGeneric();
        response.setTimestamp(new Date());
        try {
            UserDTO user = usersService.findByUsername(username);
            response.setData(user == null ? false : true);
            response.setCodeStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.setErrors(new ErrorDTO(ex.getMessage()));
            response.setCodeStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @ApiOperation(value="Metodo para revisar si un email existe o no.", response = ResponseGeneric.class)
    @GetMapping(value = "/existEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> existEmail(@RequestParam(name = "email") String email) {
        ResponseGeneric response = new ResponseGeneric();
        response.setTimestamp(new Date());
        try {
            UserDTO user = usersService.findByEmail(email);
            response.setData(user == null ? false : true);
            response.setCodeStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.setErrors(new ErrorDTO(ex.getMessage()));
            response.setCodeStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(value="Metodo para actualizar el token de un usuario", response = ResponseGeneric.class)
    @PutMapping(value = "/updateToken", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateToken(@Valid @RequestBody UpdateTokenRequest request) {
        ResponseGeneric response = new ResponseGeneric();
        response.setTimestamp(new Date());
        try {
            response.setData(usersService.updateToken(request));
            response.setCodeStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.setErrors(new ErrorDTO(ex.getMessage()));
            response.setCodeStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="Metodo para actualizar los namiusers de un usuario", response = ResponseGeneric.class)
    @PutMapping(value = "/updateNamiusers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateToken(@Valid @RequestBody UpdateNamiusersRequest request) {
        ResponseGeneric response = new ResponseGeneric();
        response.setTimestamp(new Date());
        try {
            response.setData(usersService.updateNamiusers(request));
            response.setCodeStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setErrors(new ErrorDTO(ex.getMessage()));
            response.setCodeStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="Metodo para obtenerlas configuraciones de un usuario.", response = ResponseGeneric.class)
    @GetMapping(value = "/configurations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getConfigurationsUsername(@RequestParam(name = "username") String username) {
        ResponseGeneric response = new ResponseGeneric();
        response.setTimestamp(new Date());
        try {
            response.setData(usersService.getConfigurations(username));
            response.setCodeStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.setErrors(new ErrorDTO(ex.getMessage()));
            response.setCodeStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="Metodo para guardar una configuracion", response = ResponseGeneric.class)
    @PutMapping(value = "/configuration", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveConfiguration(@Valid @RequestBody UserConfigurationDTORequest request) {
        ResponseGeneric response = new ResponseGeneric();
        response.setTimestamp(new Date());
        try {
        	request.getConfigurations().setCreated(LocalDateTime.now());
            response.setData(usersService.saveConfiguration(request));
            response.setCodeStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.setErrors(new ErrorDTO(ex.getMessage()));
            response.setCodeStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value="Metodo para obtenerlas configuraciones de un usuario.", response = ResponseGeneric.class)
    @GetMapping(value = "/email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendEmail(@RequestParam(name = "email") String email, @RequestParam(name = "username") String username,
                                       @RequestParam(name = "type") Integer type) {
        ResponseGeneric response = new ResponseGeneric();
        response.setTimestamp(new Date());
        try {
            usersService.sendEmail(email, username, type);
            response.setData("email sended");
            response.setCodeStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            response.setErrors(new ErrorDTO(ex.getMessage()));
            response.setCodeStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
