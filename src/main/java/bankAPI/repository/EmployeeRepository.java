package bankAPI.repository;

import bankAPI.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Boolean existsByUsername(String username);
}
