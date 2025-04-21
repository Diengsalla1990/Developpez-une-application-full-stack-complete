package com.openclassrooms.mddapi.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Model class representing a subject entity.
 */
@Data
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(
  name = "theme",
  uniqueConstraints = { @UniqueConstraint(columnNames = "name") }
)
public class Theme{

  /** The unique identifier of the subject. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  /** le nom du théme. */
  @NonNull
  @Size(max = 60)
  @Column(name = "name")
  private String name;

  /** La description du theme. */
  @NonNull
  @Size(min = 30)
  @Column(name = "description")
  private String description;

  /** Liste Article associé avec le théme. */
  @OneToMany(
    mappedBy = "theme",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.LAZY
  )
  private List<Article> articles;

  /** liste de user associer avec le théme. */
  @ManyToMany(mappedBy = "themes")
  private List<User> users;
  
}
