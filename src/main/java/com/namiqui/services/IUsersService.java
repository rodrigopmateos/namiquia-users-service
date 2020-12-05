package com.namiqui.services;

import java.util.List;
import com.namiqui.domain.UserD;
import com.namiqui.models.ChangePasswordRequest;
import com.namiqui.models.RecoverPasswordRequest;
import com.namiqui.models.UpdateNamiusersRequest;
import com.namiqui.models.UpdateTokenRequest;
import com.namiqui.models.UserAlertInfo;
import com.namiqui.models.UserConfigurationDTORequest;
import com.namiqui.models.UserConfigurationDTOResponse;
import com.namiqui.models.UserDTO;
import com.namiqui.models.UserRegisterRequest;
import com.namiqui.models.UserUpdateRequest;
import com.namiqui.models.UserUpdateResponse;
import com.namiqui.models.UserUpgradeRequest;


/**
 * Interfaz del servicio de usuarios
 */
public interface IUsersService {

    /**
     * Funcion para realizar el registro de un usuario
     * @param r Request con data
     * @return
     */
    UserDTO register(UserRegisterRequest r) throws Exception;

    UserDTO createUser(UserRegisterRequest r) throws Exception;

    /**
     * Funcion para Recupera password, se envia un password nuevo al email
     * @param r
     * @return
     */
    Boolean recovery(RecoverPasswordRequest r) throws Exception;


    Boolean recoveryByCode(RecoverPasswordRequest r) throws Exception;

    /**
     * Funcion para actualizar el password de un usuario
     * @param r Informaicon para actualizar
     * @return
     */
    Boolean changePassword(ChangePasswordRequest r) throws Exception;

    Boolean changePasswordByCode(ChangePasswordRequest r) throws Exception;

    /**
     * Funcioón para actulizar datos del usuario
     * @param r Informacion del usuario
     * @return
     */
    UserUpdateResponse updateInfo(UserUpdateRequest r) throws Exception;

    UserDTO updateInfoByAdmin(UserRegisterRequest r) throws Exception;

    UserDTO findByUsername(String username);

    UserDTO findByEmail(String username);

    UserAlertInfo getNamiusers(String username);

    UserAlertInfo getNamiusersById(Long id);

    //CompletableFuture<User> findByUsernameAsync(String username) throws InterruptedException;

    //CompletableFuture<User> findByEmailAsync(String email) throws InterruptedException;

    List<UserD> getAllUsersEnabled(Boolean enabled) throws Exception;

    List<UserD> getAllUsers() throws Exception;

    UserD upgrade(UserUpgradeRequest r) throws Exception;

    UpdateTokenRequest updateToken(UpdateTokenRequest r) throws Exception;

    UserDTO updateNamiusers(UpdateNamiusersRequest r) throws Exception;

    UserConfigurationDTOResponse getConfigurations(String username) throws Exception;

    UserConfigurationDTOResponse getConfiguration(String username, String keyName) throws Exception;

    UserConfigurationDTOResponse saveConfiguration(UserConfigurationDTORequest cfgInfo) throws Exception;

    void sendEmail(String email, String username, Integer type);

    UserDTO findById(Long id);
}
