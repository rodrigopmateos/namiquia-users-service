package com.namiqui.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.namiqui.constants.ApiConstants;
import com.namiqui.domain.Address;
import com.namiqui.domain.NamiquiUser;
import com.namiqui.domain.NamiquiUserId;
import com.namiqui.domain.RecoveryPassword;
import com.namiqui.domain.RecoveryPasswordId;
import com.namiqui.domain.UserAvatar;
import com.namiqui.domain.UserD;
import com.namiqui.domain.UserPackage;
import com.namiqui.models.ChangePasswordRequest;
import com.namiqui.models.NamiUserDTO;
import com.namiqui.models.NamiUserRequest;
import com.namiqui.models.NamiuserAlertInfo;
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
import com.namiqui.respository.IConfigurationCatalogRepository;
import com.namiqui.respository.IDataTypeRepository;
import com.namiqui.respository.IRecoveryPasswordControlRepository;
import com.namiqui.respository.IRolesRepository;
import com.namiqui.respository.IUserConfigurationRepository;
import com.namiqui.respository.IUserImagePredefinedRepository;
import com.namiqui.respository.IUserRepository;
import com.namiqui.utils.PwdUtil;
import com.namiqui.utils.StringGeneratorEnum;
import com.namiqui.utils.TransformDtos;
import com.namiqui.utils.TransformEntity;

@Service
public class UsersService implements IUsersService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UsersService.class);

  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private IRolesRepository rolesRepository;

  @Autowired
  private IRecoveryPasswordControlRepository recoveryPasswordControlRepository;

  @Autowired
  private IUserImagePredefinedRepository userImagePredefinedRepository;

  @Autowired
  private IUserConfigurationRepository userConfigurationRepository;

  /**
   * 
   */
  @Autowired
  private IConfigurationCatalogRepository configurationCatalogRepository;

  @Autowired
  private IDataTypeRepository dataTypeRepository;

  @Autowired
  private IEmailService emailService;

  /**
   * Variable que apunta a la clase de constantes.
   */
  @Autowired
  private ApiConstants apiConstants;

  @Override
  public UserDTO register(UserRegisterRequest r) throws Exception {
    LOGGER.info("Inicia proceso de registro de usuario");
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    UserDTO u = new UserDTO();

    LOGGER.info("Busqueda de usuario por email...");
    if (userRepository.countByEmail(r.getUser().getEmail()) > 0) {
      LOGGER.error("El email ingresado no esta disponible.!");
      throw new Exception("El email ingresado no esta disponible.");
    } else if (userRepository.countByUsername(r.getUser().getUsername()) > 0) {
      LOGGER.error("Username no disponible.");
      throw new Exception("Username no disponoble.");
    } else if (r.getUser().getNamiusers() == null || r.getUser().getNamiusers().isEmpty()) {
      LOGGER.error("Debe ingresar dos Namiusers, prueba con namiqui1 y namiqui2");
      throw new Exception("Namiusers son requeridos");
    } else {
      UserD user = TransformDtos.convertToUser(r.getUser());
      LOGGER.info("Asignando ROL defaul USER_FREE");
      UserPackage role = rolesRepository.findByName("USER_FREE");
      Optional<UserAvatar> defaultImage = userImagePredefinedRepository.findById(1l);
      if (defaultImage.isPresent())
        user.setAvatar(defaultImage.get());
      user.setUserPackage(role);

      for (NamiUserDTO n : r.getUser().getNamiusers()) {
        UserD namiUser = this.userRepository.findByUsername(n.getUsername());

        if (namiUser != null) {
          user.getNamiquiUsers()
              .add(NamiquiUser.builder()
                  .id(NamiquiUserId.builder().userNamiquiId(namiUser.getUserId())
                      .userId(user.getUserId()).build())
                  .user(user).created(LocalDateTime.now()).build());
        } else {
          throw new Exception(String.format("Namiuser: %s not exist.", n.getUsername()));
        }
      }


      // Encode password
      user.setPassword(passwordEncoder.encode(r.getUser().getPassword()));
      // Save user

      user.getUserConfiguration().setUser(user);
      user.getAddress().setUser(user);
      user.setCreated(LocalDateTime.now());

      u = TransformEntity.convertToUser(userRepository.save(user));
      this.createNamiusers(user, u);
      LOGGER.info(u.toString());
      // u.getNamiusers().forEach(x -> {
      // UserD nami = this.userRepository.findByUsername(x.getUsername());
      // x.setName(nami.getName());
      // // x.setUserImage(nami.getAvatarId().intValue());
      // // x.setImageDefaultUrl(nami.getUserImageUrl()); // TODO: asignar la foto correpsondiente
      // // cuando es de tipo photo u otro
      // });

      LOGGER.info(u.toString());
      emailService.sendEmail("Registro de usuario Namiqui.", r.getUser().getEmail(),
          String.format("Felicidades!, se ha registrado el usuario %s.", r.getUser().getUsername()),
          2);
    }

    LOGGER.info("Registro terminado");
    return u;
  }

  @Override
  public UserDTO createUser(UserRegisterRequest r) throws Exception {
    LOGGER.info("Creacion de Usuario por el administrador");
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    UserDTO u = new UserDTO();

    LOGGER.info("Buscado email si ya existe");
    if (userRepository.countByEmail(r.getUser().getEmail()) > 0) {
      LOGGER.error("Correo proporcionado no disponible.!");
      throw new Exception("Correo proporcionado no disponible.");
    } else if (userRepository.countByUsername(r.getUser().getUsername()) > 0) {
      LOGGER.error("Username no disponible.");
      throw new Exception("Username no disponible.");
    } else if (r.getUser().getRole() == null) {
      LOGGER.error("Debe ingresar un paquete");
      throw new Exception("Roles son requeridos");
    } else {

      UserD user = TransformDtos.convertToUser(r.getUser());
      // Encode password
      user.setPassword(passwordEncoder.encode(r.getUser().getPassword()));
      // Save user
      LOGGER.info(user.toString());

      u = TransformEntity.convertToUser(userRepository.save(user));

      this.createNamiusers(user, u);
      // u.getNamiusers().forEach(x -> {
      // UserD nami = this.userRepository.findByUsername(x.getUsername());
      // x.setName(nami.getName());
      // // x.setUserImage(nami.getAvatarId().intValue());
      // // x.setImageDefaultUrl(nami.getUserImageUrl()); // TODO: asignar la foto correpsondiente
      // // cuando es de tipo photo u otro
      // });

      LOGGER.info(u.toString());
      emailService.sendEmail("Creacion de usuario namiqui", r.getUser().getEmail(),
          String.format("Se ha creado un usuario. su contraseña es: %s", r.getUser().getPassword()),
          0);
    }

    LOGGER.info("Registro terminado!");
    return u;
  }

  @Override
  public Boolean recovery(RecoverPasswordRequest r) throws Exception {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    LOGGER.info("Inicio de recuperacion de contraseña.");

    Optional<UserD> userRecovery = userRepository.findById(r.getUserId());
    if (userRecovery.isPresent()) {
      UserD recoveryPassword = userRecovery.get();
      String pwd = PwdUtil.alphaNumericString(10, 1);
      LOGGER.info(pwd);
      recoveryPassword.setPassword(passwordEncoder.encode(pwd));

      userRepository.save(recoveryPassword);

      emailService.sendEmail("Recuperación de password", recoveryPassword.getEmail(), pwd, 1);
      LOGGER.info("Envio de correo de recuperacion de contraseña!!");
      LOGGER.info("Recuperacion de contraseña finalizado");
      return true;
    } else {
      LOGGER.error("El nombre de usuario proporcionado no existe");
      throw new Exception("El nombre de usuario proporcionado no existe");
    }
  }

  @Override
  public Boolean recoveryByCode(RecoverPasswordRequest r) throws Exception {
    // PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    LOGGER.info("Recuperacion de contraseña por token");

    LOGGER.info("Busqueda de nombre de usuario");
    Optional<UserD> userRecovery = userRepository.findById(r.getUserId());
    if (userRecovery.isPresent()) {
      UserD recoveryPassword = userRecovery.get();
      // Generate Code for change password
      // insert rrow in table for control
      // Send email with code
      LOGGER.info("Generacion de codigo para generacion de password");
      // String code = PwdUtil.alphaNumericString(6, 2);

      String code = StringGeneratorEnum.DIGITS.apply(6);
      LOGGER.info(code);

      LOGGER.info(LocalDateTime.now().toString());
      LOGGER.info(LocalDateTime.now().plusMinutes(30).toString());

      RecoveryPassword requested = RecoveryPassword.builder()
          .id(RecoveryPasswordId.builder().userId(recoveryPassword.getUserId()).build()).code(code)
          .updated(0).validAt(LocalDateTime.now().plusMinutes(30)).created(LocalDateTime.now())
          .user(recoveryPassword).build();


      LOGGER.info("Insertar en la base de datos nuevo password");
      recoveryPasswordControlRepository.save(requested);

      emailService.sendEmail("Código de recuperación de password", recoveryPassword.getEmail(),
          code, 1);
      LOGGER.info("Envio de correo de recuperacion de contraseña!!");
      LOGGER.info("Recuperacion de contraseña finalizado");
      return true;
    } else {
      LOGGER.error("El nombre de usuario proporcionado no existe");
      throw new Exception("El nombre de usuario proporcionado no existe");
    }
  }

  @Override
  public Boolean changePassword(ChangePasswordRequest r) throws Exception {
    LOGGER.info("Inicio de cambio de contraseña");
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    UserD userChangePwd = userRepository.findByUsername(r.getUsername());

    if (userChangePwd != null) {
      LOGGER.info("Validando contraseña...");

      if (passwordEncoder.matches(r.getOldPassword(), userChangePwd.getPassword())) {
        LOGGER.info("Actualizando contraseña...");
        userChangePwd.setPassword(passwordEncoder.encode(r.getNewPassword()));
        userRepository.save(userChangePwd);
        LOGGER.info("Contraseña cambiada!!");

        emailService.sendEmail("Cambio de password", userChangePwd.getEmail(),
            String.format("Tu password se cambio correctamente"), 0);
        LOGGER.info("Finalizando cambio de contraseña");
        return true;
      } else {
        LOGGER.error("Nombre de usuario o contraseña incorrecto!!");
        throw new Exception("Nombre de usuario o contraseña incorrecto!!");
      }
    } else {
      LOGGER.error("Nombre de usuario o contraseña incorrecto!!");
      throw new Exception("Nombre de usuario o contraseña incorrecto!!");
    }
  }

  @Override
  public Boolean changePasswordByCode(ChangePasswordRequest r) throws Exception {
    LOGGER.info("Inicia cambio de contraseña por codigo");
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    UserD userChangePwd = userRepository.findByUsername(r.getUsername());

    if (userChangePwd != null) {
      LOGGER.info("Validando contraseña...");

      RecoveryPassword match = recoveryPasswordControlRepository
          .findByIdUserIdAndCodeAndUpdated(userChangePwd.getUserId(), r.getCode(), 0);

      if (match == null) {
        LOGGER.error("El codigo para cambio de contraseña no coincide!!");
        throw new Exception("El codigo para cambio de contraseña no coincide!!");
      }

      LOGGER.info(LocalDateTime.now().toString());
      LOGGER.info(" > ");
      // LOGGER.info(match.get().toString());
      if (match.getValidAt().isBefore(LocalDateTime.now())) {
        LOGGER.error("El codigo proporcionado ya expiro!!");
        throw new Exception("El codigo proporcionado ya expiro!!");
      }

      /*
       * if (!passwordEncoder.matches(r.getOldPassword(), userChangePwd.getPassword())) {
       * LOGGER.error("User or password incorrect!!"); throw new
       * Exception("User or password incorrect!!"); }
       */

      LOGGER.info("Actualizando contraseña...");
      userChangePwd.setPassword(passwordEncoder.encode(r.getNewPassword()));
      userRepository.save(userChangePwd);
      LOGGER.info("Contraseña cambiado!!");

      match.setUpdated(1);
      match.setUpdatedAt(LocalDateTime.now());
      recoveryPasswordControlRepository.save(match);

      emailService.sendEmail("Cambio de password", userChangePwd.getEmail(),
          String.format("Tu password se cambio correctamente"), 0);
      LOGGER.info("Finaliza cambio de contraseña con codigo");
      return true;

    } else {
      LOGGER.error("El nombre de usuario no existe");
      throw new Exception("El nombre de usuario no existe");
    }
  }

  @Override
  public UserUpdateResponse updateInfo(UserUpdateRequest r) throws Exception {
    LOGGER.info("Inicia actualizacion de informacion de usuario...");
    UserDTO u = new UserDTO();
    UserUpdateResponse result = new UserUpdateResponse();
    UserD userUpdate = userRepository.findByUsername(r.getUsername());
    if (userUpdate != null) {
      LOGGER.info("Actualizando informacion de usuario...");
      // userUpdate.setAddress(r.getAddress());

      Address address = userUpdate.getAddress();
      address.setAddress(r.getAddress().getAddress());
      address.setPostalCode(r.getAddress().getPostalCode());
      address.setStateId(r.getAddress().getStateId());
      address.setColony(r.getAddress().getColony());
      address.setCity(r.getAddress().getCity());

      UserAvatar avatar = userImagePredefinedRepository.getOne(r.getUserImage());
      // if (ui == null) {
      if (avatar == null) {
        avatar = userImagePredefinedRepository.getOne(1l);
      }
      // userUpdate.setUserImage(ui);
      // userUpdate.setAvatarId(ui.getId().longValue());
      // userUpdate.setUserImageUrl(r.getUserImageUrl());

      // Save user

      userUpdate.setAvatar(avatar);
      u = TransformEntity.convertToUser(userRepository.save(userUpdate));
      LOGGER.info(u.toString());
      // u.getNamiusers().forEach(x -> {
      // UserD nami = this.userRepository.findByUsername(x.getUsername());
      // x.setName(nami.getName());
      // // x.setUserImage(nami.getUserImage().getId());
      // // x.setUserImage(nami.getAvatarId().intValue());
      // });

      result = TransformEntity.conrtToUserResponse(userUpdate);

      LOGGER.info(u.toString());
    } else {
      LOGGER.info("El usuario proporcionado no existe.");
      throw new Exception("El usuario proporcionado no existe.");
    }

    LOGGER.info("Finaliza actualizacion de informacion de usuario...");
    return result;
  }

  @Override
  public UserDTO updateInfoByAdmin(UserRegisterRequest userToModify) throws Exception {
    LOGGER.info("Inicia actualizacion de informacion de usuario por el Administrador...");
    UserDTO u;

    UserD userUpdate = userRepository.findByUsername(userToModify.getUser().getUsername());
    if (userUpdate != null) {
      LOGGER.info("Actualizando informacion de usuario...");

      UserD userModified = TransformDtos.convertToUser(userToModify.getUser());

      u = TransformEntity.convertToUser(userRepository.save(userModified));

      this.createNamiusers(userModified, u);

      LOGGER.info(u.toString());
    } else {
      LOGGER.info("El usuario ingresado no coincide.");
      throw new Exception("El usuario ingresado no coincide.");
    }

    LOGGER.info("Finaliza proceso de actualizacion de informacion...");
    return u;
  }

  /**
   * Metodo expuesto para login atraves de oauth
   *
   * @param username
   * @return
   */
  @Override
  public UserDTO findByUsername(String username) {
    LOGGER.info("Inicia busqueda de usuario por username...");
    UserDTO user;
    try {
      UserD u = this.userRepository.findByUsername(username);

      user = TransformEntity.convertToUser(u);

      this.createNamiusers(u, user);

      LOGGER.info(user.toString());
    } catch (Exception ex) {
      LOGGER.error(ex.getMessage());
      return null;
    }
    return user;
  }

  @Override
  public UserDTO findByEmail(String email) {
    LOGGER.info("Inicia busqueda de usuario por email...");
    UserDTO user = new UserDTO();
    UserD userModel = this.userRepository.findByEmail(email);
    user = TransformEntity.convertToUser(userModel);
    this.createNamiusers(userModel, user);
    return user;
  }

  @Override
  public UserAlertInfo getNamiusers(String username) {
    UserAlertInfo userAlertInfo = new UserAlertInfo();

    UserD u = this.userRepository.findByUsername(username);
    if (u == null) {
      LOGGER.error("Username sin namiusers");
      return userAlertInfo;
    }

    userAlertInfo.setUsername(username);
    userAlertInfo.setName(u.getName());
    // userAlertInfo.setUserImage(u.getUserImage().getId());
    // userAlertInfo.setUserImage(u.getAvatarId().intValue());

    // LOGGER.info(u.getNamiquiUsers().toString());
    u.getNamiquiUsers().forEach(n -> {
      // UserD nami = this.userRepository.findByUsername(n.getUsername());
      // Duda, como obtener el username de namiquiUser
      Optional<UserD> nami = this.userRepository.findById(n.getId().getUserNamiquiId());
      // LOGGER.info(nami.toString());
      NamiuserAlertInfo namiUserAlert = new NamiuserAlertInfo();
      namiUserAlert.setFcmToken(nami.get().getFcmToken());
      namiUserAlert.setEmail(nami.get().getEmail());
      namiUserAlert.setPhone(nami.get().getPhone());
      namiUserAlert.setUsername(nami.get().getUsername());
      namiUserAlert.setId(nami.get().getUserId());
      // namiUserAlert.setUserImage(nami.getAvatarId().intValue()); // TODO: cuando es de tipo photo
      // u otro asignar el valor de userImageurl

      namiUserAlert
          .setAvatar(TransformEntity.convertToAvatar(Optional.ofNullable(nami.get().getAvatar())));
      namiUserAlert.setName(nami.get().getName());
      // Duda, como obtener url de la imagen
      // namiUserAlert.setUserImageUrl(nami.getUserImage().getImageUrl());
      userAlertInfo.getNamiusers().add(namiUserAlert);
    });

    return userAlertInfo;
  }

  @Override
  public UserAlertInfo getNamiusersById(Long id) {
    UserAlertInfo userAlertInfo = new UserAlertInfo();

    Optional<UserD> u = this.userRepository.findById(id);
    if (!u.isPresent()) {
      LOGGER.error("Username sin namiusers");
      return userAlertInfo;
    }

    userAlertInfo.setUsername(u.get().getUsername());
    userAlertInfo.setName(u.get().getName());
    userAlertInfo.setId(u.get().getUserId());
    userAlertInfo.setUserImageUrl(u.get().getAvatar().getImageUrl());

    u.get().getNamiquiUsers().forEach(n -> {
      Optional<UserD> nami = this.userRepository.findById(n.getId().getUserNamiquiId());
      NamiuserAlertInfo namiUserAlert = new NamiuserAlertInfo();
      namiUserAlert.setFcmToken(nami.get().getFcmToken());
      namiUserAlert.setEmail(nami.get().getEmail());
      namiUserAlert.setPhone(nami.get().getPhone());
      namiUserAlert.setUsername(nami.get().getUsername());
      namiUserAlert.setId(nami.get().getUserId());
      namiUserAlert
              .setAvatar(TransformEntity.convertToAvatar(Optional.ofNullable(nami.get().getAvatar())));
      namiUserAlert.setName(nami.get().getName());

      userAlertInfo.getNamiusers().add(namiUserAlert);
    });

    return userAlertInfo;
  }

  /*
   * @Async("userAsyncExecutor")
   * 
   * @Override public CompletableFuture<User> findByUsernameAsync(String username) throws
   * InterruptedException { LOGGER.info("findByUsernameAsync begins..."); User user =
   * this.userRepository.findByUsername(username); LOGGER.info("findByUsernameAsync finished...");
   * return CompletableFuture.completedFuture(user); }
   * 
   * @Async("userAsyncExecutor")
   * 
   * @Override public CompletableFuture<User> findByEmailAsync(String email) throws
   * InterruptedException { LOGGER.info("findByEmailAsync begins..."); User user =
   * this.userRepository.findByEmail(email); LOGGER.info("findByEmailAsync finished..."); return
   * CompletableFuture.completedFuture(user); }
   */

  @Override
  public List<UserD> getAllUsersEnabled(Boolean enabled) throws Exception {
    return this.userRepository.getAllByEnabled(enabled);
  }

  @Override
  public List<UserD> getAllUsers() throws Exception {
    return this.userRepository.findAll();
  }

  @Override
  public UserD upgrade(UserUpgradeRequest r) throws Exception {
    UserD user = userRepository.findByUsername(r.getUsername());

    if (user != null) {

      user.getUserPackage().setId(r.getMembership());

      userRepository.save(user);
    } else {
      throw new Exception("Se ingreso un nombre de usuario invalido.");
    }

    return user;
  }

  @Override
  public UpdateTokenRequest updateToken(UpdateTokenRequest r) throws Exception {
    LOGGER.info("Inicia actualizacion de token...");

    UserDTO u = new UserDTO();

    UserD userUpdate = userRepository.findByUsername(r.getUsername());
    if (userUpdate != null) {
      LOGGER.info("Actualizando informacion de usuario...");

      userUpdate.setFcmToken(r.getFmcToken());
      // Save user
      LOGGER.info(userUpdate.toString());

      u = TransformEntity.convertToUser(userRepository.save(userUpdate));

      LOGGER.info(u.toString());
    } else {
      LOGGER.info("El usuario que se ingreso no existe.");
      throw new Exception("El usuario que se ingreso no existe.");
    }

    LOGGER.info("Finaliza actualizacion de token...");

    return r;
  }

  @Override
  public UserDTO updateNamiusers(UpdateNamiusersRequest r) throws Exception {
    LOGGER.info("Inicia actualizacion de Namiusers...");

    UserDTO u = new UserDTO();
    if (r.getNamiusers().isEmpty()) {
      throw new Exception("Se requiere un Namiuser");
    }

    UserD userUpdate = userRepository.findByUsername(r.getUsername());
    if (userUpdate == null) {
      LOGGER.info("El namiuser proporcionado no existe.");
      throw new Exception("El namiuser proporcionado no existe.");

    }

    // userUpdate.getNamiusers().clear();
    userUpdate.getNamiquiUsers().clear();

    for (NamiUserRequest nu : r.getNamiusers()) {
      r.getNamiusers().subList(r.getNamiusers().indexOf(nu) + 1, r.getNamiusers().size());
      Optional<NamiUserRequest> repeated = r.getNamiusers()
          .subList(r.getNamiusers().indexOf(nu) + 1, r.getNamiusers().size()).stream()
          .filter(namiuser -> namiuser.getUsername().equals(nu.getUsername())).findAny();
      if (repeated.isPresent())
        throw new Exception("No se deben de repetir los namiusers.");
      Optional<UserD> namiUser =
          Optional.ofNullable(userRepository.findByUsername(nu.getUsername()));
      if (namiUser.isPresent()) {
        UserD user = namiUser.get();
        if (!r.getUsername().equals(nu.getUsername())) {
          // NamiUser namiuser = new NamiUser();
          // NamiquiUser namiuser = new NamiquiUser();
          // Duda, como obtener el username del namiqui
          // namiuser.setUsername(nu.getUsername());
          userUpdate.getNamiquiUsers()
              .add(NamiquiUser.builder()
                  .id(NamiquiUserId.builder().userNamiquiId(user.getUserId())
                      .userId(userUpdate.getUserId()).build())
                  .user(userUpdate).created(LocalDateTime.now()).build());
        } else {
          throw new Exception(
              String.format("No puedes utilizar el mismo nombre de usuario.", nu.getUsername()));

        }
      } else {
        throw new Exception(String.format("Namiuser: %s no existe.", nu.getUsername()));
      }
    }

    LOGGER.info("Actualizando informacion de usuario...");

    // Save user
    // LOGGER.info(userUpdate.toString());

    u = TransformEntity.convertToUser(userRepository.save(userUpdate));
    this.createNamiusers(userUpdate, u);

    LOGGER.info(u.toString());


    // u.getNamiusers().forEach(x -> {
    // UserD n = this.userRepository.findByUsername(x.getUsername());
    // x.setId(n.getId());
    // x.setUsername(n.getUsername());
    // x.setName(n.getName());
    // // x.setUserImage(n.getAvatarId().intValue());
    // // Duda, como obtener url de la imagen
    // x.setUserImageUrl("");
    // });

    LOGGER.info("Finaliza actualizacion de namiusers...");

    return u;
  }

  @Override
  public UserConfigurationDTOResponse saveConfiguration(UserConfigurationDTORequest cfgInfo)
      throws Exception {
    UserD updateConfig = userRepository.findByUsername(cfgInfo.getUsername());
    // if (u == null)
    // throw new Exception("Nombre de usuario no encontrado");
    //
    // for (ConfigurationDTO c : cfgInfo.getConfigurations()) {
    //
    // if (c.getKeyName() == null || c.getKeyName().equals("") || c.getKeyValue() == null
    // || c.getKeyValue().equals("")) {
    // throw new Exception("El key value o key name no puede ser un valor nulo o vacio");
    // }
    //
    // DataType dt = dataTypeRepository.findByName(c.getDataType());
    // if (dt == null) {
    // dt = dataTypeRepository.findByName("STRING");
    // }
    // /* ¿Un usuario puede tener varias configuraciones? */
    // // Optional<UserConfiguration> cfg = u.getConfigurations().stream().filter(x ->
    // // x.getKeyName().equals(c.getKeyName())).findFirst();
    // // if (cfg.isPresent()) {
    // // cfg.get().setTypeValue(dt);
    // // cfg.get().setKeyValue(c.getKeyValue());
    // // } else {
    // // UserConfiguration newCfg = new UserConfiguration();
    // // newCfg.setKeyName(c.getKeyName());
    // // newCfg.setKeyValue(c.getKeyValue());
    // // newCfg.setTypeValue(dt);
    // // u.getConfigurations().add(newCfg);
    // // }
    // }

    updateConfig.getUserConfiguration().setAlert911(cfgInfo.getConfigurations().getAlert911());
    updateConfig.getUserConfiguration().setAlertUser(cfgInfo.getConfigurations().getAlertUser());
    userRepository.save(updateConfig);

    UserConfigurationDTOResponse result = new UserConfigurationDTOResponse();
    result.setUsername(updateConfig.getUsername());
    result.setConfigurations(cfgInfo.getConfigurations());
    // u.getConfigurations().forEach(x -> result.getConfigurations().put(x.getKeyName(),
    // x.getKeyValue()));

    return result;
  }

  @Override
  public UserConfigurationDTOResponse getConfigurations(String username) throws Exception {
    LOGGER.info("Inicia proceso de busqueda de configuraciones de usuario...");
    LOGGER.info(username);
    UserD u = userRepository.findByUsername(username);

    if (u == null)
      throw new Exception("Nombre de usuario no existe");


    UserConfigurationDTOResponse configs = new UserConfigurationDTOResponse();
    configs.setUsername(username);
    configs.setConfigurations(
        TransformEntity.convertToConfiguration(Optional.ofNullable(u.getUserConfiguration())));
    /* ¿Un usuario puede tener varias configuraciones? */
    // TODO: evitar null en ek key y value
    // if(u.getConfigurations().size() == 0) {
    // apiConstants.getDefaultConfigs().forEach(x -> {
    // configurationCatalogRepository.findAll().stream().forEach(y -> {
    // if(y.getKeyName().equals(x))
    // configs.getConfigurations().put(y.getKeyName(), y.getKeyValue());
    // });
    // });
    // } else {
    // u.getConfigurations().forEach(x -> configs.getConfigurations().put(x.getKeyName()
    // , x.getKeyValue()));
    // }

    return configs;
  }

  @Override
  public UserConfigurationDTOResponse getConfiguration(String username, String keyName)
      throws Exception {
    LOGGER.info("Inicia proceso de busqueda de configuraciones por keyname de usuario...");
    LOGGER.info(username);
    UserD u = userRepository.findByUsername(username);

    if (u == null)
      throw new Exception("El username no se encontro.");

    UserConfigurationDTOResponse cfg = new UserConfigurationDTOResponse();

    /* ¿Un usuario puede tener varias configuraciones? */
    // u.getConfigurations().forEach(c -> {
    // if (c.getKeyName().equals(keyName)) {
    // cfg.getConfigurations().put(c.getKeyName(), c.getKeyValue());
    // }
    // });


    return cfg;
  }

  public void sendEmail(String email, String username, Integer type) {

    String mensaje = String.format("Felicidades!, se ha registrado el usuario %s.", username);
    emailService.sendEmail("Registro de usuario Namiqui.", email, mensaje, 2);
  }

  private void createNamiusers(UserD u, UserDTO user) {
    u.getNamiquiUsers().stream().forEach(nami -> {
      Optional<UserD> namiUser = this.userRepository.findById((nami.getId().getUserNamiquiId()));
      user.getNamiusers().add(TransformEntity.convertToNamiuser(namiUser.get()));
    });
  }

  @Override
  public UserDTO findById(Long id) {
    LOGGER.info("Inicia busqueda de usuario por username...");
    UserDTO user;
    try {
      Optional<UserD> u = this.userRepository.findById(id);

      user = TransformEntity
          .convertToUser(u.orElseThrow(() -> new Exception("Usuario no encontrado")));

      this.createNamiusers(u.get(), user);

      LOGGER.info(user.toString());
    } catch (Exception ex) {
      LOGGER.error(ex.getMessage());
      return null;
    }
    return user;
  }
}
