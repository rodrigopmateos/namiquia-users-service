package com.namiqui.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.namiqui.domain.UserAvatar;
import com.namiqui.models.UserAvatarDto;
import com.namiqui.respository.IUserImagePredefinedRepository;
import com.namiqui.utils.TransformDtos;
import com.namiqui.utils.TransformEntity;

@Service
public class UserImagePredefinedService implements IUserImagePredefinedService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserImagePredefinedService.class);

  @Autowired
  private IUserImagePredefinedRepository userImagePredefinedRepository;

  @Override
  public List<UserAvatarDto> getUserImagesAll() throws Exception {
    List<UserAvatarDto> result = new ArrayList<>();
    List<UserAvatar> all = userImagePredefinedRepository.findAll();

    all.forEach(x -> result.add(TransformEntity.convertToAvatar(Optional.ofNullable(x))));

    return result;
  }

  @Override
  public UserAvatarDto saveUserImagePredefined(UserAvatarDto request)
      throws Exception {

    try {
      UserAvatar userImage = TransformDtos.convertToAvatar(Optional.ofNullable(request));
      userImage = userImagePredefinedRepository.save(userImage);
      request.setId(userImage.getId().longValue());
      request.setCreatedAt(userImage.getCreated());
    } catch (Exception ex) {
      LOGGER.error(ex.getMessage());
      throw new Exception("Ocurrio error al guardar imagen predeterminada");
    }

    LOGGER.info("Proceso de imagen terminado.");

    return null;
  }
}
