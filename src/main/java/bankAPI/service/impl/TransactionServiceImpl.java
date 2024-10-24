package bankAPI.service.impl;

import bankAPI.entity.Customer;
import bankAPI.entity.Transaction;
import bankAPI.exception.BankAPIException;
import bankAPI.exception.ResourceNotFoundException;
import bankAPI.repository.CustomerRepository;
import bankAPI.repository.TransactionRepository;
import bankAPI.service.CustomerService;
import bankAPI.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  CustomerRepository customerRepository,
                                  CustomerService customerService) {
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Transaction saveTransaction(Long customerID, Double amount, String transactionType) {
        Customer customer = customerRepository
                .findById(customerID)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "Customer ID", customerID));

        double newCustomerBalance = customer.getBalance();
        double beforeBalance = 0;

        if(transactionType.equalsIgnoreCase("withdraw"))
            beforeBalance = newCustomerBalance + amount;
        else if(transactionType.equalsIgnoreCase("deposit"))
            beforeBalance = newCustomerBalance - amount;
        else    throw new BankAPIException(HttpStatus.BAD_REQUEST, "Invalid transaction type.");

        Transaction newTransaction = new Transaction();
        newTransaction.setTransactionDate(new Date(System.currentTimeMillis()));
        newTransaction.setBeforeBalance(beforeBalance);
        newTransaction.setNewBalance(newCustomerBalance);
        newTransaction.setAmount(amount);
        newTransaction.setTransactionType(transactionType);

        return transactionRepository.save(newTransaction);
    }

    @Override
    public List<Transaction> viewAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> viewTransactionByCustomerID(Long customerID) {
        return transactionRepository.findByCustomerId(customerID);
    }
}
