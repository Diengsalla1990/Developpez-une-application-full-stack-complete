package com.openclassrooms.mddapi.util.entityAndDtoCreation.factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.util.payload.Request.RegisterRequest;

/**
 * Classe Factory pour la création d'entités DbUser et de DTO.
 */
@Component
public class Userfactory {

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  /**
  * Convertit une charge utile RegisterRequest en entité DbUser.
  *
  * @param registerRequest La charge utile RegisterRequest.
  * @return L'entité DbUser correspondante.
  */
  public User getUserEntityFromRegisterRequest(
    RegisterRequest registerRequest
  ) {
    User user = new User(
      registerRequest.getEmail(),
      registerRequest.getUsername(),
      bCryptPasswordEncoder.encode(registerRequest.getPassword())
    );
    return user;
  }

  /**
  * Convertit une entité DbUser en DbUserDto.
  *
  * @param user L'entité DbUser.
  * @return Le DbUserDto correspondant.
  */

  public UserDto getUserDTOFromEntity(User user) {
    UserDto dbUserDto = new UserDto()
      .setId(user.getId())
      .setEmail(user.getEmail())
      .setUsername(user.getUsername())
      .setPassword(user.getPassword());
    return dbUserDto;
  }

  /**
  * Convertit une entité DbUser avec des Theme associés en un DbUserDto avec des identifiants de sujet.
  *
  * @param dbUser L'entité DbUser avec les Thémes associés.
  * @return Le DbUserDto correspondant avec les identifiants de sujet.
  */
  public UserDto getUserWithSubDtoFromEntityWithSub(User User) {
    List<Long> themeIds = new ArrayList<>();
    for (Theme theme : User.getThemes()) {
    	themeIds.add(theme.getId());
    }
    Collections.sort(themeIds);

    UserDto dbUserWithSubjectIdsDto = new UserDto(
      User.getId(),
      User.getEmail(),
      User.getUsername(),
      User.getPassword(),
      themeIds
    );
    return dbUserWithSubjectIdsDto;
  }
}