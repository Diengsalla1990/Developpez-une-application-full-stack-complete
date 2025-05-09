package com.openclassrooms.mddapi.serviceInterface;

import java.util.List;
import java.util.Optional;
import com.openclassrooms.mddapi.model.Theme;

/**
 * Interface for managing Subject entities.
 */
public interface ThemeInterface {
 
  List<Theme> getAllTheme();

  Optional<Theme> getThemeById(long id);

 
  Optional<Theme> getThemeByIdWithPost(long id);
}