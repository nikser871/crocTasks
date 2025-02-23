package org.example;

import org.example.dao.TransactionDAO;
import org.example.dao.impl.TransactionDAOImpl;
import org.example.service.AnalyzeTransactionsService;
import org.example.service.impl.AnalyzeTransactionsServiceImpl;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        String filePath = "transaction.csv";

        TransactionDAO dao = new TransactionDAOImpl();
        AnalyzeTransactionsService service = new AnalyzeTransactionsServiceImpl(dao);

        service.analyzeTransactions(filePath);
    }
}