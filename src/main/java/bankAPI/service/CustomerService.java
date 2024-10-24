package bankAPI.service;

import bankAPI.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer registerCustomer(Customer newCustomer);
    boolean deposit(Long customerID, Double amount);
    boolean withdraw(Long customerID, Double amount);
    String checkBalance(Long customerID);
    boolean transfer(Long senderID, Long recipientID, Double amount);
    List<Customer> viewAllCustomers(Long employeeID);
    String approveCustomer(Long employeeID, Long customerID);
}
