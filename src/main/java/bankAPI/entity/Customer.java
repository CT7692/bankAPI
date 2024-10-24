package bankAPI.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer extends Employee{

    @NotEmpty(message = "Birthday cannot be null.")
    @Column(nullable = false)
    private String birthday;

    @NotEmpty(message = "Balance cannot be null.")
    @Column(nullable = false)
    private Double balance;

    private Boolean isApproved = false;

    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Transaction> transactionList = new ArrayList<>();
}
