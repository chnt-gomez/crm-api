package com.vmartinez.crm.repository;

import com.vmartinez.crm.dto.TransactionQuery;
import com.vmartinez.crm.entity.Transaction;

import java.util.List;

public interface SmartQueryRepository {
    List<Transaction> executeQuery(TransactionQuery query);
}
