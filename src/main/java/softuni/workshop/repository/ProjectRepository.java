package softuni.workshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.workshop.domain.entities.Project;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    Project findProjectByName(String name);

    List<Project> findAllByFinishedIsTrue();

    List<Project> findAllByNameEndingWithOrderByPaymentDesc(String name);
}
