package org.example.dao.impl;

import org.example.dao.TransactionDAO;
import org.example.model.Transaction;
import org.example.service.enums.TransactionType;
import org.example.utils.DateValidatorUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {

    @Override
    public List<Transaction> readTransactions(String filePath) {
        return translateDataFromStrings(getTransactions(filePath));
    }

    /**
     * Метод для получения данных из транзакций
     * @param filePath
     * @return массив полученных данных из файла
     */
    private List<String[]> getTransactions(String filePath) {
        List<String[]> transactions = new ArrayList<>();

        try (FileReader reader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                String[] transaction = line.split(",");
                transactions.add(transaction);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return transactions;
    }

    /**
     * Метод позволяет перенести данные из файла в сущности
     * @param transactions массив данных из транзакций
     * @return Массив, состоящий из моделек-статистик для каждого вида транзакций
     */
    private List<Transaction> translateDataFromStrings(List<String[]> transactions) {
        List<Transaction> results = new ArrayList<>();

        for (String[] transaction : transactions) {
            Transaction t = getTransactionFromString(transaction);

            if (t != null) {
                results.add(t);
            }
        }

        return results;
    }

    /**
     * Получить сущность транзакции
     * @param transaction одна строка из файла с данными
     * @return
     */
    private Transaction getTransactionFromString(String[] transaction) {
        String date = transaction[1];
        String transactionType = transaction[2];
        String amount = transaction[3];

        if (       date != null
                && transactionType != null
                && amount != null
                && DateValidatorUtil.isValidDate(date)) {
            LocalDate dateOfTransaction = LocalDate.parse(date);
            TransactionType type = TransactionType.valueOf(transactionType);
            BigDecimal sum = new BigDecimal(amount);
            return new Transaction(type, dateOfTransaction, sum);
        } else {
            return null;
        }
    }
}
