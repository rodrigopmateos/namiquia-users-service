package com.namiqui.services;

import com.namiqui.domain.UserD;
import com.namiqui.models.ChangePasswordRequest;
import com.namiqui.models.NamiUser;
import com.namiqui.models.NamiUserDTO;
import com.namiqui.models.RecoverPasswordRequest;
import com.namiqui.models.User;
import com.namiqui.models.UserDTO;
import com.namiqui.models.UserRegisterRequest;
import com.namiqui.models.UserUpdateRequest;
import com.namiqui.models.UserUpgradeRequest;
import com.namiqui.respository.IUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class UsersServiceTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IEmailService emailService;


    @InjectMocks
    private UsersService usersService;

    private UserRegisterRequest USER_REGISTER_REQUEST;
    private UserD USER = new UserD();

    private RecoverPasswordRequest RECOVERY_PASSWORD_REQUEST;

    private ChangePasswordRequest CHANGE_PASSWORD_REQUEST = new ChangePasswordRequest();

    private UserUpdateRequest USER_UPDATE_REQUEST = new UserUpdateRequest();

    private UserUpgradeRequest USER_UPGRADE_REQUEST = new UserUpgradeRequest();

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
//
//        USER_REGISTER_REQUEST = new UserRegisterRequest();
//        USER_REGISTER_REQUEST.setUser(new UserDTO());
//        USER_REGISTER_REQUEST.getUser().setUsername("user1");
//        USER_REGISTER_REQUEST.getUser().setPhone("email");
//        USER_REGISTER_REQUEST.getUser().setPassword("Password");
//        USER_REGISTER_REQUEST.getUser().setName("name");
//        USER_REGISTER_REQUEST.getUser().setEmail("email@email.com");
//        USER_REGISTER_REQUEST.getUser().setAddress("address");
//        USER_REGISTER_REQUEST.getUser().setFcmToken("TOKEN");
//        NamiUserDTO nami1 = new NamiUserDTO();
//        nami1.setUsername("nami1");
//        USER_REGISTER_REQUEST.getUser().setNamiusers(new ArrayList<>());
//        USER_REGISTER_REQUEST.getUser().getNamiusers().add(nami1);
//
//        //USER = new User(USER_REGISTER_REQUEST.getUser());
////        USER = new UserD(USER_REGISTER_REQUEST.getUser());
//        USER.setId(1L);
//
//        RECOVERY_PASSWORD_REQUEST = new RecoverPasswordRequest();
//        RECOVERY_PASSWORD_REQUEST.setUsername("username");
//
//        CHANGE_PASSWORD_REQUEST.setUsername("username");
//        CHANGE_PASSWORD_REQUEST.setOldPassword("oldpassword");
//        CHANGE_PASSWORD_REQUEST.setNewPassword("newpassword");
//
//        USER_UPDATE_REQUEST.setUsername("username");
//        USER_UPDATE_REQUEST.setPhone("5555555555");
//        USER_UPDATE_REQUEST.setAddress("address");
//        USER_UPDATE_REQUEST.setName("name");
//
//        USER_UPGRADE_REQUEST.setUsername("username");

    }

    /**
     * Test for register new Test
     * @throws Exception
     */
    /*@Test
    public void registerOKTest() throws Exception{
        Mockito.when(userRepository.countByEmail("email")).thenReturn(0);
        Mockito.when(userRepository.countByUsername("username")).thenReturn(0);
        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenReturn(USER);
        Mockito.doNothing().when(emailService).sendEmail("subject", "email", "message", 2);


        final UserDTO result = usersService.register(USER_REGISTER_REQUEST);
        System.out.println(result);
        assertNotNull(result);
    }*/

    @Test(expected = Exception.class)
    public void registerEmailExistFailTest() throws Exception{
        Mockito.when(userRepository.countByEmail(Mockito.anyString())).thenReturn(1);

        final UserDTO result = usersService.register(USER_REGISTER_REQUEST);
        //assertNotNull(result);
        fail();

    }

    @Test(expected = Exception.class)
    public void registerUsernameExistFailTest() throws Exception{
        Mockito.when(userRepository.countByEmail(Mockito.anyString())).thenReturn(0);
        Mockito.when(userRepository.countByUsername(Mockito.anyString())).thenReturn(1);
        usersService.register(USER_REGISTER_REQUEST);
        fail();

    }

    @Test(expected = Exception.class)
    public void registerNoNamiUserProvidedTest() throws Exception{
//        Mockito.when(userRepository.countByUsername(Mockito.anyString())).thenReturn(0);
//        Mockito.when(userRepository.countByUsername(Mockito.anyString())).thenReturn(0);
//        USER_REGISTER_REQUEST.getUser().setNamiusers(null);
//        usersService.register(USER_REGISTER_REQUEST);
//        fail();
    }

    @Test
    public void recoveryOKTest() throws Exception {
        Mockito.when(userRepository.findByUsername("username")).thenReturn(USER);
        Mockito.when(userRepository.save(Mockito.any(UserD.class)))
                .thenReturn(USER);
        Mockito.doNothing().when(emailService).sendEmail("subject", "email", "message", 0);

        final Boolean emailSended = usersService.recovery(RECOVERY_PASSWORD_REQUEST);
        assertEquals(true, emailSended);
    }

    @Test(expected = Exception.class)
    public void recoveryFailTest() throws Exception {
        Mockito.when(userRepository.findByUsername("username")).thenReturn(null);
        usersService.recovery(RECOVERY_PASSWORD_REQUEST);
        fail();
    }

    @Test
    public void changePasswordOKTest()throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        USER.setPassword(passwordEncoder.encode("test"));
        Mockito.when(userRepository.findByUsername("username")).thenReturn(USER);
        CHANGE_PASSWORD_REQUEST.setOldPassword("test");
        final Boolean result = usersService.changePassword(CHANGE_PASSWORD_REQUEST);
        assertEquals(true, result);
    }


    @Test(expected = Exception.class)
    public void changePasswordWrongPasswordFailTest()throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        USER.setPassword(passwordEncoder.encode("test"));
        Mockito.when(userRepository.findByUsername("username")).thenReturn(USER);
        CHANGE_PASSWORD_REQUEST.setOldPassword("test2");
        usersService.changePassword(CHANGE_PASSWORD_REQUEST);
        fail();
    }

    @Test(expected = Exception.class)
    public void changePasswordUserNotExistFailTest()throws Exception {
        Mockito.when(userRepository.findByUsername("username")).thenReturn(null);
        usersService.changePassword(CHANGE_PASSWORD_REQUEST);
        fail();
    }

    /*@Test
    public void updateInfoOKTest() throws Exception{
        Mockito.when(userRepository.findByUsername("username")).thenReturn(USER);
        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenReturn(USER);
        UserDTO result = usersService.updateInfo(USER_UPDATE_REQUEST);
        assertNotNull(result);
    }*/

    @Test(expected = Exception.class)
    public void updateInfoUserNotExistFailTest() throws Exception{
        Mockito.when(userRepository.findByUsername("username")).thenReturn(null);

        usersService.updateInfo(USER_UPDATE_REQUEST);
        fail();

    }

    @Test
    public void upgradeUserOkTest()throws Exception{
        Mockito.when(userRepository.findByUsername("username")).thenReturn(USER);
        Mockito.when(userRepository.save(Mockito.any(UserD.class)))
                .thenReturn(USER);

//        USER_UPGRADE_REQUEST.setMembership("USER_FREE");
//
//        UserD result = usersService.upgrade(USER_UPGRADE_REQUEST);
//        assertNotNull(result);
//
//        USER_UPGRADE_REQUEST.setMembership("USER_PRO");
//
//        result = usersService.upgrade(USER_UPGRADE_REQUEST);
//        assertNotNull(result);
//
//        USER_UPGRADE_REQUEST.setMembership("USER_PREMIUM");
//
//        result = usersService.upgrade(USER_UPGRADE_REQUEST);
//        assertNotNull(result);
    }

    @Test(expected = Exception.class)
    public void upgradeUserFailTest()throws Exception{
//        USER_UPGRADE_REQUEST.setMembership("USER_FREE");
//        Mockito.when(userRepository.findByUsername("username")).thenReturn(null);
//        usersService.upgrade(USER_UPGRADE_REQUEST);
//        fail();
    }

}
