package com.namiqui.controllers;

import com.namiqui.domain.UserD;
import com.namiqui.models.ChangePasswordRequest;
import com.namiqui.models.RecoverPasswordRequest;
import com.namiqui.models.ResponseGeneric;
import com.namiqui.models.User;
import com.namiqui.models.UserDTO;
import com.namiqui.models.UserRegisterRequest;
import com.namiqui.models.UserUpdateRequest;
import com.namiqui.models.UserUpdateResponse;
import com.namiqui.models.UserUpgradeRequest;
import com.namiqui.services.IUsersService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.Before;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UsersControllersTest {

    /**
     * User services Mock
     */
    @Mock
    private IUsersService usersService;

    /**
     * Class to test
     */
    @InjectMocks
    private UserController userController;

    /**
     * TEST NEW REGISTER
     */
    private UserRegisterRequest USER_REGISTER_REQUEST = new UserRegisterRequest();
    private UserDTO USER_DTO = new UserDTO();

    private RecoverPasswordRequest RECOVERY_PASSWORD_REQUEST = new RecoverPasswordRequest();

    private ChangePasswordRequest CHANGE_PASSWORD_REQUEST = new ChangePasswordRequest();

    private UserUpdateRequest USER_UPDATE_USER = new UserUpdateRequest();

    private UserUpgradeRequest USER_UPGRADE_REQUEST = new UserUpgradeRequest();
    /**
     * Initialize objects
     */
    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);

//        // Initialize object for test register User
//        USER_REGISTER_REQUEST.setUser(new UserDTO());
//        USER_REGISTER_REQUEST.getUser().setAddress("address");
//        USER_REGISTER_REQUEST.getUser().setEmail("email");
//        USER_REGISTER_REQUEST.getUser().setName("name");
//        USER_REGISTER_REQUEST.getUser().setPassword("password");
//        USER_REGISTER_REQUEST.getUser().setPhone("5555555555");
//        USER_REGISTER_REQUEST.getUser().setUsername("username");
//
//        // Initialize object for response
//        USER_DTO.setId(1L);
//        USER_DTO.setName("name");
//        USER_DTO.setEmail("email");
//        USER_DTO.setPassword("passwordEncripted");
//        USER_DTO.setPhone("5555555555");
//        USER_DTO.setUsername("username");
//        USER_DTO.setNamiusers(new ArrayList<>());
//
//        RECOVERY_PASSWORD_REQUEST.setUsername("username");
//
//        CHANGE_PASSWORD_REQUEST.setUsername("username");
//        CHANGE_PASSWORD_REQUEST.setOldPassword("oldPassword");
//        CHANGE_PASSWORD_REQUEST.setNewPassword("newPassword");
//
//        USER_UPDATE_USER.setName("name");
//        USER_UPDATE_USER.setAddress("address");
//        USER_UPDATE_USER.setPhone("55555555555");
//        USER_UPDATE_USER.setUsername("username");
//
//        USER_UPGRADE_REQUEST.setUsername("username");
//        USER_UPGRADE_REQUEST.setMembership("USER_FREE");
//
//        // Mockito when register what return.
//        Mockito.when(usersService.register(USER_REGISTER_REQUEST)).thenReturn(new UserDTO());
//
//        Mockito.when(usersService.recovery(RECOVERY_PASSWORD_REQUEST)).thenReturn(true);
//
//        Mockito.when(usersService.updateInfo(USER_UPDATE_USER)).thenReturn(new UserUpdateResponse());
//
//        Mockito.when(usersService.upgrade(USER_UPGRADE_REQUEST)).thenReturn(new UserD());
    }

    @Test
    public void helloWorldTest(){
        final String result = userController.helloWorld();
        // assertNotNull(hello);
        assertEquals("Hello World", result);
    }

    @Test
    public void registerOkTest() throws Exception{
        final ResponseEntity<?> response = userController.register(USER_REGISTER_REQUEST);

        assertNotNull(response);
        assertNotNull(((ResponseGeneric)response.getBody()).getData());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void registerFailTest() throws Exception{
        // Returning a Exception to validate INTERNAL SERVER ERROR
        Mockito.when(usersService.register(USER_REGISTER_REQUEST)).thenThrow(new Exception("FAILS"));

        final ResponseEntity<?> response = userController.register(USER_REGISTER_REQUEST);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void recoveryOkTest(){
        final ResponseEntity<?> response = userController.recovery(RECOVERY_PASSWORD_REQUEST);

        assertNotNull(response);
        assertNotNull(((ResponseGeneric)response.getBody()).getData());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void recoveryFailTest() throws Exception{
        // Returning a Exception to validate INTERNAL SERVER ERROR
        Mockito.when(usersService.recovery(RECOVERY_PASSWORD_REQUEST)).thenThrow(new Exception("FAILS"));

        final ResponseEntity<?> response = userController.recovery(RECOVERY_PASSWORD_REQUEST);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void changePasswordOkTest(){
        final ResponseEntity<?> response = userController.changePassword(CHANGE_PASSWORD_REQUEST);

        assertNotNull(response);
        assertNotNull(((ResponseGeneric)response.getBody()).getData());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void changePasswordFailTest() throws Exception{
        // Returning a Exception to validate INTERNAL SERVER ERROR
        Mockito.when(usersService.changePassword(CHANGE_PASSWORD_REQUEST)).thenThrow(new Exception("FAILS"));

        final ResponseEntity<?> response = userController.changePassword(CHANGE_PASSWORD_REQUEST);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void updateInfoOkTest(){
        final ResponseEntity<?> response = userController.updateUser(USER_UPDATE_USER);

        assertNotNull(response);
        assertNotNull(((ResponseGeneric)response.getBody()).getData());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void updateinfoFailTest() throws Exception{
        // Returning a Exception to validate INTERNAL SERVER ERROR
        Mockito.when(usersService.updateInfo(USER_UPDATE_USER)).thenThrow(new Exception("FAILS"));

        final ResponseEntity<?> response = userController.updateUser(USER_UPDATE_USER);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void upgradeUserOkTest(){
        final ResponseEntity<?> response = userController.upgradeUser(USER_UPGRADE_REQUEST);

        assertNotNull(response);
        assertNotNull(((ResponseGeneric)response.getBody()).getData());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void upgradeUserFailTest() throws Exception{
        // Returning a Exception to validate INTERNAL SERVER ERROR
        Mockito.when(usersService.upgrade(USER_UPGRADE_REQUEST)).thenThrow(new Exception("FAILS"));

        final ResponseEntity<?> response = userController.upgradeUser(USER_UPGRADE_REQUEST);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


    /*@Test
    public void getAllUsersUserOkTest(){
        final ResponseEntity<?> response = userController.getAll(true);

        assertNotNull(response);
        assertNotNull(((ResponseGeneric)response.getBody()).getData());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getAllUsersFailTest() throws Exception{
        // TODO Review Exception
        // Returning a Exception to validate INTERNAL SERVER ERROR
        Mockito.when(usersService.getAllEnabled(true)).thenThrow(new Exception("FAILS"));

        final ResponseEntity<?> response = userController.getAll(true);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }*/



    @Test
    public void searchByUsernameOkTest(){
        final ResponseEntity<?> response = userController.existUsername("username");

        assertNotNull(response);
        assertNotNull(((ResponseGeneric)response.getBody()).getData());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void existUsernameOkTest(){
        final ResponseEntity<?> response = userController.existUsername("username");

        assertNotNull(response);
        assertNotNull(((ResponseGeneric)response.getBody()).getData());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void existUsernameFailTest() throws Exception{
        // TODO Review Exception
        // Returning a Exception to validate INTERNAL SERVER ERROR
        Mockito.when(usersService.findByUsername("username")).thenReturn(null);

        final ResponseEntity<?> response = userController.existUsername("username");
        assertEquals(false, ((ResponseGeneric)response.getBody()).getData());
    }
}
