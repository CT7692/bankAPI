package bankAPI.repository;

import bankAPI.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Boolean existsByUsername(String username);
}
