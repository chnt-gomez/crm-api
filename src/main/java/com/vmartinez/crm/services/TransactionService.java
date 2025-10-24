package com.vmartinez.crm.services;

import com.vmartinez.crm.entity.Transaction;
import com.vmartinez.crm.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return repository.findById(id);
    }

    public Transaction createTransaction(Transaction transaction) {
        return repository.save(transaction);
    }

    public Transaction updateTransaction(Long id, Transaction updatedTransaction) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setType(updatedTransaction.getType());
                    existing.setCustomerId(updatedTransaction.getCustomerId());
                    existing.setValue(updatedTransaction.getValue());
                    existing.setTimestamp(updatedTransaction.getTimestamp());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Transaction not found with id " + id));
    }

    public void deleteTransaction(Long id) {
        repository.deleteById(id);
    }

    public List<Transaction> getTransactionsByCustomerId(Long customerId) {
        return repository.findByCustomerId(customerId);
    }
}
