package bankAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import bankAPI.entity.Transaction;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCustomerId(Long customerID);
}
