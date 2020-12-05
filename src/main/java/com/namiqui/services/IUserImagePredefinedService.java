package com.namiqui.services;

import java.util.List;
import javax.validation.Valid;
import com.namiqui.models.UserAvatarDto;

public interface IUserImagePredefinedService {

    List<UserAvatarDto> getUserImagesAll() throws Exception;

    UserAvatarDto saveUserImagePredefined(@Valid UserAvatarDto request) throws Exception;
}
