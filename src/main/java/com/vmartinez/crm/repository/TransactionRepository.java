package com.vmartinez.crm.repository;

import com.vmartinez.crm.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository  extends JpaRepository<Transaction, Long>, SmartQueryRepository {

    List<Transaction> findByCustomerId(Long customerId);
}
