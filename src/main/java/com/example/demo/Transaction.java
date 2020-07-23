package com.example.demo;

import lombok.Data;

import java.time.Instant;
import java.time.OffsetDateTime;

@Data
public class Transaction {

    private String customerId;

    private OffsetDateTime transactionDate;

    private Double transactionAmount;
}
