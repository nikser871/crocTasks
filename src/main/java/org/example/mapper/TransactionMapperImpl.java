package org.example.mapper;

import org.example.model.Transaction;
import org.example.service.enums.TransactionType;
import org.example.validator.ValidationStrategy;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionMapperImpl implements Mapper<Transaction, String[]> {

    private final ValidationStrategy<String> validationStrategy;

    public TransactionMapperImpl(ValidationStrategy<String> validationStrategy) {
        this.validationStrategy = validationStrategy;
    }

    @Override
    public Transaction toEntity(String[] transaction) {
        String date = transaction[1];
        String transactionType = transaction[2];
        String amount = transaction[3];

        if (date != null
                && transactionType != null
                && amount != null
                && validationStrategy.isValid(date)) {
            LocalDate dateOfTransaction = LocalDate.parse(date);
            TransactionType type = TransactionType.valueOf(transactionType);
            BigDecimal sum = new BigDecimal(amount);
            return new Transaction(type, dateOfTransaction, sum);
        }

        return null;
    }
}
