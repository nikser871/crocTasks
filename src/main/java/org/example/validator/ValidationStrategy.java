package org.example.validator;

public interface ValidationStrategy<T> {

    boolean isValid(T t);

}
