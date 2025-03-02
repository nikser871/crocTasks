package org.example.service.impl;

import org.example.dao.TransactionDAO;
import org.example.model.Transaction;
import org.example.service.AnalyzeTransactionsService;
import org.example.service.enums.TransactionType;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.util.Objects.nonNull;
import static org.example.service.enums.TransactionType.DEBIT;

public class AnalyzeTransactionsServiceImpl implements AnalyzeTransactionsService {

    private final TransactionDAO transactionDAO;

    private static final String COUNT_OF_TRANSACTION = "Количество транзакций:";
    private static final String AVERAGE_SUM = "Средняя сумма:";
    private static final String DAY_WITH_BIG_SUM = "День с наибольшими расходами ";
    private static final String UNIQUE_DATES_OF_TRANSACTIONS = "Уникальные даты транзакций:";
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public AnalyzeTransactionsServiceImpl(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    @Override
    public void analyzeTransactions(InputStream inputStream) {
        List<Transaction> transactions = transactionDAO.readTransactions(inputStream);

        System.out.println(COUNT_OF_TRANSACTION);
        printInformationFromStatistics(getCountOfTransactions(transactions));

        System.out.println(AVERAGE_SUM);
        printInformationFromStatistics(getCountOfTransactions(transactions));

        printDateOfMaxSumOfTransaction(transactions, DEBIT);

        System.out.println(UNIQUE_DATES_OF_TRANSACTIONS);
        printInformationFromStatistics(getUniqueDateTransactions(transactions));
    }

    /**
     * Вывод стат. данных для первого блока (Количество)
     * @param transactions транзакции
     */
    private List<String> getCountOfTransactions(List<Transaction> transactions) {
        return Arrays.stream(TransactionType.values())
                .map(type -> getCountOfTransactions(transactions,type))
                .toList();
    }

    /**
     * Вывод стат. данных для первого блока (Количество)
     */
    private String getCountOfTransactions(List<Transaction> transactions, TransactionType type) {
        long count = transactions.stream()
                .filter(transaction -> transaction.type().equals(type))
                .count();

        return "- " + type.getValue() + ": " + count;
    }

    /**
     * Вывод стат. данных для второго блока (Средняя сумма для каждого типа)
     * @param transactions транзакции
     */
    private List<String> getCountAverageFromTransactions(List<Transaction> transactions) {
        return Arrays.stream(TransactionType.values())
                .map(type -> getCountAverageFromTransactions(transactions,type))
                .toList();
    }

    /**
     *  Вывод стат. данных для второго блока (Средняя сумма для каждого типа)
     */
    private String getCountAverageFromTransactions(List<Transaction> transactions, TransactionType type) {
        BigDecimal average = BigDecimal.valueOf(transactions.stream()
                .filter(transaction -> transaction.type().equals(type))
                .map(Transaction::sum)
                .mapToDouble(BigDecimal::doubleValue)
                .average().orElseThrow());

        return "- " + type.getValue() + ": " + average;
    }

    /**
     * Вывод стат. данных для третьего блока (День с наибольшими расходами)
     * @param transactions транзакции
     */
    private void printDateOfMaxSumOfTransaction(List<Transaction> transactions, TransactionType type) {
        printDateOfMaxSumOfTransaction(type);
        LocalDate date = transactions.stream()
                .filter(transaction -> nonNull(transaction) && transaction.type().equals(type))
                .max(Comparator.comparing(Transaction::sum))
                .get().date();

        System.out.println(date.format(DATE) + "\n");
    }

    private void printDateOfMaxSumOfTransaction(TransactionType type) {
        System.out.print(DAY_WITH_BIG_SUM + " (" + type.getValue() + "): ");
    }

    /**
     * Выводит список уникальных дат для транзакций
     * @param transactions Транзакции
     */
    private List<String> getUniqueDateTransactions(List<Transaction> transactions) {
        return transactions.stream()
                .map(Transaction::date)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .map(date -> "- " + date.format(DATE))
                .toList();
    }

    /**
     * @param results результаты после анализа транзазакций для определенного блока
     */
    private void printInformationFromStatistics(List<String> results) {
        results.forEach(System.out::println);
        System.out.println();
    }


}
