package bankAPI.entity;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date transactionDate;

    @Column(nullable = false)
    private Double beforeBalance;

    @Column(nullable = false)
    private Double newBalance;

    @NotEmpty(message = "Transaction amount cannot be null.")
    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String transactionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "customer_id",
            nullable = false
    )
    private Customer customer;
}
