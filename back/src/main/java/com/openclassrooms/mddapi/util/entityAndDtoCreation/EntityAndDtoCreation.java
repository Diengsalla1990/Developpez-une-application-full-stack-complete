package com.openclassrooms.mddapi.util.entityAndDtoCreation;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.util.entityAndDtoCreation.factory.Userfactory;
import com.openclassrooms.mddapi.util.payload.Request.RegisterRequest;

/**
 * Utility class for creating entities and DTOs.
 */
@Component
public class EntityAndDtoCreation {

  @Autowired
  private Userfactory dbUserFactory;

  

  /**
   * Converts a RegisterRequest object to a DbUser entity.
   *
   * @param registerRequest The RegisterRequest object to convert.
   * @return The corresponding DbUser entity.
   */
  public User getDbUserFromRegisterRequest(RegisterRequest registerRequest) {
    return dbUserFactory.getUserEntityFromRegisterRequest(registerRequest);
  }

  
}