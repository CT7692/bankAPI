package bankAPI.controller;

import bankAPI.entity.Transaction;
import bankAPI.exception.BankAPIException;
import bankAPI.service.CustomerService;
import bankAPI.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/customer")
@RestController
public class CustomerController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/{customerID}/{transactionType}")
    public ResponseEntity<Transaction> makeTransaction(
            @PathVariable("customerID") Long customerID,
            @RequestBody Double amount,
            @PathVariable("transactionType") String transactionType) {

        if(transactionType.equalsIgnoreCase("withdraw"))
            customerService.withdraw(customerID, amount);
        else if(transactionType.equalsIgnoreCase("deposit"))
            customerService.deposit(customerID, amount);

        Transaction transaction = transactionService.saveTransaction(customerID, amount, transactionType);

        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/{customerID}/balance")
    public ResponseEntity<String> getBalance(@PathVariable("customerID") Long customerID) {

        String strBalance = customerService.checkBalance(customerID);
        return ResponseEntity.ok(strBalance);
    }

    @PostMapping("/{loggedInCustomerID}/transfer/{recipientID}")
    public ResponseEntity<List<Transaction>> makeTransfer(
            @PathVariable("loggedInCustomerID") Long senderID,
            @PathVariable("recipientID") Long recipientID,
            @RequestBody Double amount
    ) {
        List<Transaction> bothTransactions = new ArrayList<>();
        Transaction senderWithdrawal = null;
        Transaction recipientDeposit = null;

        boolean transferSuccessful = customerService.transfer(senderID, recipientID, amount);

        if(transferSuccessful) {
            senderWithdrawal = transactionService.saveTransaction(senderID, amount, "withdraw");
            recipientDeposit = transactionService.saveTransaction(recipientID, amount, "deposit");
            bothTransactions.add(senderWithdrawal);
            bothTransactions.add(recipientDeposit);
        }
        else throw new BankAPIException(HttpStatus.BAD_REQUEST, "Error occurred during the transfer.");

        return ResponseEntity.ok(bothTransactions);
    }

    //TODO: View transactions by Customer ID.
    @GetMapping("/{loggedInCustomerID}/transactions")
    public ResponseEntity<List<Transaction>> viewCustomerTransactions(
            @PathVariable("loggedInCustomerID") Long customerID
    ) {
        return ResponseEntity.ok(transactionService.viewTransactionByCustomerID(customerID));
    }
}
