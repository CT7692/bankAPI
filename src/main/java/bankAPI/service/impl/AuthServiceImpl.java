package bankAPI.service.impl;

import bankAPI.entity.Customer;
import bankAPI.entity.Employee;
import bankAPI.payload.LoginDTO;
import bankAPI.payload.RegisterDTO;
import bankAPI.repository.CustomerRepository;
import bankAPI.repository.EmployeeRepository;
import bankAPI.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public String login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "Login successful.";
    }

    @Override
    public String register(RegisterDTO registerDTO, String role) {

        boolean customerUsernameExists = customerRepository.existsByUsername(
                registerDTO.getUsername());
        boolean employeeUsernameExists = employeeRepository.existsByUsername(
                registerDTO.getUsername());

        if(customerUsernameExists && employeeUsernameExists)
            return "Username taken";

        if(role.equalsIgnoreCase("employee")) {
            Employee employee = new Employee();
            employee.setName(registerDTO.getName());
            employee.setUsername(registerDTO.getUsername());
            employee.setPassword(passwordEncoder.encode(
                    registerDTO.getPassword()));
            employeeRepository.save(employee);
        }

        else if(role.equalsIgnoreCase("customer")) {
            Customer customer = new Customer();
            customer.setName(registerDTO.getName());
            customer.setUsername(registerDTO.getUsername());
            customer.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

            customerRepository.save(customer);
        }

        else
            return "Invalid role.";

        return "User registration successful.";
    }

}
