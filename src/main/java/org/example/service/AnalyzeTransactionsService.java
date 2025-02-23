package org.example.service;

/**
 * Интерфейс Анализа транзакций
 */
public interface AnalyzeTransactionsService  {

    /**
     * Данный метод принимает путь к файлу
     * и делает анализ данных с последующим выводом в консоль
     * @param filePath путь к файлу
     */
    void analyzeTransactions(String filePath);

}
