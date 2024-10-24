package bankAPI.controller;

import bankAPI.entity.Customer;
import bankAPI.entity.Transaction;
import bankAPI.service.CustomerService;
import bankAPI.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/employee")
@RestController
public class EmployeeController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/{employeeID}/customers")
    public ResponseEntity<List<Customer>> viewAllCustomers(
            @PathVariable("employeeID") Long employeeID
    ) {
        return ResponseEntity.ok(customerService.viewAllCustomers(employeeID));
    }

    @GetMapping("/{employeeID}/transactions")
    public ResponseEntity<List<Transaction>> viewAllTransactions(
            @PathVariable("employeeID") Long employeeID
    ) {
        return ResponseEntity.ok(transactionService.viewAllTransactions());
    }

    @GetMapping("/{employeeID}/customer/{customerID}/transactions")
    public ResponseEntity<List<Transaction>> viewTransactionsByCustomerID(
            @PathVariable("employeeID") Long employeeID,
            @PathVariable("customerID") Long customerID
    ) {
        return ResponseEntity.ok(transactionService.viewTransactionByCustomerID(customerID));
    }

    @PatchMapping("/{employeeID}/customer/{customerID}/approve")
    public ResponseEntity<String> approveCustomer(
            @PathVariable("employeeID") Long employeeID,
            @PathVariable("customerID") Long customerID
    ) {
        return ResponseEntity.ok(customerService.approveCustomer(employeeID, customerID));
    }
}
