package org.example.mapper;

public interface Mapper<T, V> {

    T toEntity(V v);
}
