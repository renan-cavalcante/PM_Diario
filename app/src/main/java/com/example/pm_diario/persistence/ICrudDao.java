package com.example.pm_diario.persistence;

import java.sql.SQLException;
import java.util.List;

public interface ICrudDao<T> {
    void insert(T t) throws SQLException;
    void update(T t) throws SQLException;
    void delete(T t) throws SQLException;
    T findById(int id) throws SQLException;
    List<T> findAll() throws SQLException;
    List<T> findByData(String data) throws SQLException;
}
