package app.taskmanager.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

  void deleteById(Long id);

  boolean existsByTitle(String title);
}
