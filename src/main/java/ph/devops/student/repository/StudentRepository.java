package ph.devops.student.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ph.devops.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {
}