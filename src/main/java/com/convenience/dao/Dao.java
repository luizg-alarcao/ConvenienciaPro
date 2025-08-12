package com.convenience.dao;

//adicionei essa interface para ajudar na criacao de outros DAO no futuro

import java.util.List;
import java.util.Optional;

public interface Dao<T, ID> {
    Optional<T> findById(ID id);
    List<T> findAll();
    T save(T entity) throws RuntimeException;
    T update(T entity) throws RuntimeException;
    void delete(T entity) throws RuntimeException;
    void deleteById(ID id) throws RuntimeException;
}
