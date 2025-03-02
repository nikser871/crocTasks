package org.example.service;

import java.io.InputStream;

/**
 * Интерфейс Анализа транзакций
 */
public interface AnalyzeTransactionsService  {

    /**
     * Данный метод принимает путь к файлу
     * и делает анализ данных с последующим выводом в консоль
     * @param inputStream поток
     */
    void analyzeTransactions(InputStream inputStream);

}
