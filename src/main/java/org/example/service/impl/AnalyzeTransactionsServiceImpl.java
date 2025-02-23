package org.example.service.impl;

import org.example.dao.TransactionDAO;
import org.example.model.Transaction;
import org.example.service.AnalyzeTransactionsService;
import org.example.service.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Formatter;
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
    public void analyzeTransactions(String filePath) {
        List<Transaction> transactions = transactionDAO.readTransactions(filePath);

        printCountOfTransactions(transactions);
        printCountAverageFromTransactions(transactions);
        printDateOfMaxSumOfTransaction(transactions, DEBIT);
        printUniqueDateTransactions(transactions);
    }

    /**
     * Вывод стат. данных для первого блока (Количество)
     * @param transactions транзакции
     */
    private void printCountOfTransactions(List<Transaction> transactions) {
        System.out.println(COUNT_OF_TRANSACTION);
        Arrays.stream(TransactionType.values())
                .forEach(type -> printCountOfTransactions(transactions,type));

        System.out.println();
    }

    /**
     * Вывод стат. данных для первого блока (Количество)
     */
    private void printCountOfTransactions(List<Transaction> transactions, TransactionType type) {
        long count = transactions.stream()
                .filter(transaction -> transaction.type().equals(type))
                .count();

        System.out.println("- " + type.getValue() + ": " + count);
    }

    /**
     * Вывод стат. данных для второго блока (Средняя сумма для каждого типа)
     * @param transactions транзакции
     */
    private void printCountAverageFromTransactions(List<Transaction> transactions) {
        System.out.println(AVERAGE_SUM);
        Arrays.stream(TransactionType.values())
                .forEach(type -> printCountAverageFromTransactions(transactions,type));

        System.out.println();
    }

    /**
     *  Вывод стат. данных для второго блока (Средняя сумма для каждого типа)
     */
    private void printCountAverageFromTransactions(List<Transaction> transactions, TransactionType type) {
        BigDecimal average = BigDecimal.valueOf(transactions.stream()
                .filter(transaction -> transaction.type().equals(type))
                .map(Transaction::sum)
                .mapToDouble(big -> big.doubleValue())
                .average().orElse(0.0));

        System.out.println("- " + type.getValue() + ": " + average);
    }

    /**
     * Вывод стат. данных для третьего блока (День с наибольшими расходами)
     * @param transactions транзакции
     */
    private void printDateOfMaxSumOfTransaction(List<Transaction> transactions, TransactionType type) {
        System.out.print(DAY_WITH_BIG_SUM + " (" + type.getValue() + "): ");
        LocalDate date = transactions.stream()
                .filter(transaction -> nonNull(transaction) && transaction.type().equals(type))
                .max(Comparator.comparing(Transaction::sum))
                .get().date();

        System.out.println(date.format(DATE));
        System.out.println();
    }

    /**
     * Выводит список уникальных дат для транзакций
     * @param transactions Транзакции
     */
    private void printUniqueDateTransactions(List<Transaction> transactions) {
        System.out.println(UNIQUE_DATES_OF_TRANSACTIONS);
        transactions.stream()
                .map(Transaction::date)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .forEach(date ->  System.out.println("- " + date.format(DATE)));
    }


}
