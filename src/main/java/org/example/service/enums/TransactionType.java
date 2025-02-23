package org.example.service.enums;

/**
 * Типы транзакций
 */
public enum TransactionType {

    /**
     * Дебет
     */
    DEBIT("DEBIT"),

    /**
     * Кредит
     */
    CREDIT("CREDIT");

    private final String value;

    public String getValue() {
        return value;
    }

    TransactionType(String debit) {
        this.value = debit;
    }
}
