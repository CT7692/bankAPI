package bankAPI.service.impl;

import bankAPI.entity.Customer;
import bankAPI.entity.Employee;
import bankAPI.entity.Transaction;
import bankAPI.exception.BankAPIException;
import bankAPI.exception.ResourceNotFoundException;
import bankAPI.repository.CustomerRepository;
import bankAPI.repository.EmployeeRepository;
import bankAPI.repository.TransactionRepository;
import bankAPI.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Customer registerCustomer(Customer newCustomer) {
        return customerRepository.save(newCustomer);
    }

    @Override
    public boolean deposit(Long customerID, Double amount) {

        boolean transactionSuccessful = false;

        Customer customer = customerRepository
                .findById(customerID)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "Customer ID", customerID));

        if (customer.getIsApproved()) {
            if(amount <= 0)
                throw new BankAPIException(HttpStatus.BAD_REQUEST, "Amount must be above 0.");
            else {
                double customerBalance = customer.getBalance();
                customer.setBalance(customerBalance + amount);
                transactionSuccessful = true;
            }
        }

        return transactionSuccessful;
    }

    @Override
    public boolean withdraw(Long customerID, Double amount) {

        boolean transactionSuccessful = false;

        Customer customer = customerRepository
                .findById(customerID)
                .orElseThrow(() ->new ResourceNotFoundException("Customer", "Customer ID", customerID));

        if (customer.getIsApproved()) {
            double customerBalance = customer.getBalance();

            if(amount < customerBalance) {
                transactionSuccessful = true;
                customer.setBalance(customerBalance - amount);
            }

            else if(amount <= 0)
                throw new BankAPIException(HttpStatus.BAD_REQUEST, "Please enter a valid amount");
        }

        return transactionSuccessful;
    }

    @Override
    public String checkBalance(Long customerID) {
        Customer customer = customerRepository
                .findById(customerID)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "Customer ID", customerID));

        DecimalFormat df = new DecimalFormat("$#,##0.00");
        return df.format(customer.getBalance());
    }

    @Override
    public boolean transfer(Long senderID, Long recipientID, Double amount) {

        boolean success = false;

        Customer sender = customerRepository
                .findById(senderID)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "Customer ID", senderID));

        Customer recipient = customerRepository
                .findById(recipientID)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "Customer ID", recipientID));

        boolean withdrawalSuccessful = withdraw(senderID, amount);
        boolean depositSuccessful = deposit(recipientID, amount);

        if(withdrawalSuccessful && depositSuccessful)
            success = true;

        return success;
    }

    @Override
    public String approveCustomer(Long employeeID, Long customerID) {
        Employee employee = employeeRepository
                .findById(employeeID)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "Employee ID", employeeID));

        Customer customer = customerRepository
                .findById(customerID)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "Customer ID", customerID));

        customer.setIsApproved(true);

        return "Customer " + customer.getUsername() + "'s account has been approved for use.";
    }

    @Override
    public List<Customer> viewAllCustomers(Long employeeID) {

        Employee employee = employeeRepository
                .findById(employeeID)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "Employee ID", employeeID));

        return customerRepository.findAll();
    }
}
