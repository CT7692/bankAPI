package bankAPI.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    private String name;

    @NotEmpty(message = "Username cannot be null.")
    @Size(min = 5, message = "Username must have at least 5 characters.")
    private String username;

    @NotEmpty(message = "Password cannot be null.")
    @Size(min = 3, message = "Password must have at least 5 characters")
    private String password;
}
