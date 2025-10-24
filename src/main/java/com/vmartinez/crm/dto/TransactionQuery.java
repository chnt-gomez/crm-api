package com.vmartinez.crm.dto;

import com.vmartinez.crm.entity.Transaction;
import lombok.Data;

import java.time.Instant;

@Data
public class TransactionQuery {
    private Long customerId;
    private Transaction.TransactionType type;
    private Instant timestampStart;
    private Instant timestampEnd;
    private String value;
}
