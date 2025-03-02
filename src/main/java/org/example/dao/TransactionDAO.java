package org.example.dao;

import org.example.model.Transaction;

import java.io.InputStream;
import java.util.List;

public interface TransactionDAO {

    /**
     * Получить список транзакицй из файла
     * @param inputStream Поток, из которого будут читаться транзакции
     * @return
     */
    List<Transaction> readTransactions(InputStream inputStream);
}
