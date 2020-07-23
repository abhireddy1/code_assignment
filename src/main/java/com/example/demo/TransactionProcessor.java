package com.example.demo;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionProcessor {

    private static final String FIRST_MONTH = "FIRST_MONTH";
    private static final String SECOND_MONTH = "SECOND_MONTH";
    private static final String THIRD_MONTH = "THIRD_MONTH";
    private static final String TOTAL = "TOTAL_REWARDS";

    public static Map<String, Map<String, Double>> process(List<Transaction> transactions) {

        Map<String, Map<String, Double>> customerTotals = new HashMap<>();

        for (Transaction transaction: transactions) {

            if (!isDateInRange(transaction)) {
                continue;
            }

            if (customerTotals.containsKey(transaction.getCustomerId())) {
                Map<String, Double> transactionTotals = customerTotals.get(transaction.getCustomerId());
                processTransaction(transaction, transactionTotals);

            } else {
                Map<String, Double> transactionTotals = new HashMap<>();
                processTransaction(transaction, transactionTotals);
                customerTotals.put(transaction.getCustomerId(), transactionTotals);
            }

        }

        return customerTotals;
    }

    private static void processTransaction(Transaction transaction, Map<String, Double> transactionTotals) {

        String currentMonth = getMonth(transaction);
        Double rewardPoints = calculateRewardPoints(transaction);

        Double total = transactionTotals.get(TOTAL);
        if (total == null) {
            total = 0d;
        }
        transactionTotals.put(TOTAL, total+rewardPoints);

        Double currentMonthRewards = transactionTotals.get(currentMonth);
        if (currentMonthRewards == null) {
            currentMonthRewards = 0d;
        }
        transactionTotals.put(currentMonth, currentMonthRewards + rewardPoints);

    }

    private static Double calculateRewardPoints(Transaction transaction) {
        Double rewardPoints = 0d;
        if (transaction.getTransactionAmount() > 50 && transaction.getTransactionAmount() <= 100) {
            rewardPoints += transaction.getTransactionAmount() - 50;
        } else if (transaction.getTransactionAmount() > 100) {
            rewardPoints += (transaction.getTransactionAmount() - 100) * 2 + 50;
        }
        return rewardPoints;
    }

    private static String getMonth(Transaction transaction) {
        String monthCode = null;
        int currentMonth = OffsetDateTime.now().getMonthValue();
        if (currentMonth - transaction.getTransactionDate().getMonthValue() == 1) {
            monthCode = FIRST_MONTH;
        } else if (currentMonth - transaction.getTransactionDate().getMonthValue() == 2) {
            monthCode = SECOND_MONTH;

        } else if (currentMonth - transaction.getTransactionDate().getMonthValue() == 3) {
            monthCode = THIRD_MONTH;
        }
        return monthCode;
    }

    private static boolean isDateInRange(Transaction transaction) {
        if (transaction.getTransactionDate() == null) {
            return false;
        }
        return OffsetDateTime.now().getMonthValue() - transaction.getTransactionDate().getMonthValue() <= 3;
    }
}
