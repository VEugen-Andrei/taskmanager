package app.taskmanager.user.repository;

import app.taskmanager.project.repository.Project;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(
  name = "users",
  uniqueConstraints = {
    @UniqueConstraint(name = "user_email_unique", columnNames = "email")
  }
)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false)
  private Long id;
  @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
  private String firstName;
  @Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
  private String lastName;
  @Column(name = "password", nullable = false, columnDefinition = "TEXT")
  private String password;
  @Column(name = "email", nullable = false, unique = true, columnDefinition = "TEXT")
  @Email
  private String email;

  @OneToMany(mappedBy = "user")
  @Cascade(CascadeType.ALL)
  List<Project> projectList;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}

