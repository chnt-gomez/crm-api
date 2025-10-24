package com.vmartinez.crm.controller;

import com.vmartinez.crm.dto.TransactionQuery;
import com.vmartinez.crm.entity.Transaction;
import com.vmartinez.crm.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/opportunity")
public class TransactionController {

    @Autowired
    private TransactionRepository repository;

    @PostMapping("/query")
    public List<Transaction> queryTransaction (@RequestBody TransactionQuery query) {
        return repository.executeQuery(query);
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return repository.save(transaction);
    }

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
