package org.example.dao.impl;

import org.example.dao.TransactionDAO;
import org.example.mapper.Mapper;
import org.example.model.Transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TransactionDAOImpl implements TransactionDAO {

    private final Mapper<Transaction, String[]> mapper;

    public TransactionDAOImpl(Mapper<Transaction, String[]> mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Transaction> readTransactions(InputStream inputStream) {
        return getTransactions(inputStream).stream()
                .map(mapper::toEntity)
                .filter(Objects::nonNull)
                .toList();
    }

    /**
     * Метод для получения данных из транзакций
     *
     * @param inputStream
     * @return массив полученных данных из файла
     */
    private List<String[]> getTransactions(InputStream inputStream) {
        List<String[]> transactions = new ArrayList<>();

        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            bufferedReader.lines().forEach(line -> {
                String[] split = line.split(",");
                transactions.add(split);
            });
        } catch (IOException e) {
            throw new RuntimeException("Произошла ошибка при попытке получить данные из файла ", e);
        }

        return transactions;
    }
}
