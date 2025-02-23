package org.example.model;

import org.example.service.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Модель-результат для хранения статистик (количество, максимум)
 *
 * @param type Тип транзакции
 * @param date Дата соверщения транзакции
 * @param sum Сумма транзакции
 */
public record Transaction(TransactionType type,
                          LocalDate date,
                          BigDecimal sum) {}
