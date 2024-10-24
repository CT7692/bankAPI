package bankAPI.service;

import bankAPI.entity.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction saveTransaction(Long customerID, Double amount, String transactionType);
    List<Transaction> viewAllTransactions();
    List<Transaction> viewTransactionByCustomerID(Long customerID);
}
