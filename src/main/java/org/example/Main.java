package org.example;

import org.example.dao.TransactionDAO;
import org.example.dao.impl.TransactionDAOImpl;
import org.example.mapper.Mapper;
import org.example.mapper.TransactionMapperImpl;
import org.example.model.Transaction;
import org.example.service.AnalyzeTransactionsService;
import org.example.service.impl.AnalyzeTransactionsServiceImpl;
import org.example.validator.DateValidationStrategyImpl;
import org.example.validator.ValidationStrategy;

import java.io.IOException;
import java.io.InputStream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {

        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("files/transaction.csv")) {
            ValidationStrategy<String> dateValidationStrategy = new DateValidationStrategyImpl();
            Mapper<Transaction, String[]> transactionMapper = new TransactionMapperImpl(dateValidationStrategy);
            TransactionDAO dao = new TransactionDAOImpl(transactionMapper);
            AnalyzeTransactionsService service = new AnalyzeTransactionsServiceImpl(dao);

            service.analyzeTransactions(inputStream);
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке или чтении файла transactions.csv: " + e.getMessage());
            e.printStackTrace();
        }


    }
}