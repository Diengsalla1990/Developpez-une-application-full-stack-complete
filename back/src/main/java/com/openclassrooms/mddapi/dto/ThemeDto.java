package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Data Transfer Object (DTO) representing a subject.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ThemeDto {

  
  @NotNull
  private long id;

  /** le nom d'un théme. */
  @NotNull
  private String name;

  /** Description d'un théme. */
  @NotNull
  private String description;

  /** Liste Article associer a un thémeT. */
  private List<ArticleDto> articleDtos;
}