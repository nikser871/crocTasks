package org.example.dao;

import org.example.model.Transaction;

import java.util.List;

public interface TransactionDAO {

    /**
     * Получить список транзакицй из файла
     * @param filePath Файл, из которого считываются транзакции
     * @return
     */
    List<Transaction> readTransactions(String filePath);
}
