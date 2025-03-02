package org.example.model;

import org.example.service.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Сущность транзакции
 *
 * @param type Тип транзакции
 * @param date Дата совершения транзакции
 * @param sum Сумма транзакции
 */
public record Transaction(TransactionType type,
                          LocalDate date,
                          BigDecimal sum) {}
