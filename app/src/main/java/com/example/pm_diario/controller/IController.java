package com.example.pm_diario.controller;

import java.sql.SQLException;
import java.util.List;

public interface IController<T> {
    void inserir(T t) throws SQLException;
    void modificar(T t) throws SQLException;
    void deletar(T t) throws SQLException;
    T buscar(int id) throws SQLException;
    List<T> listar() throws SQLException;
    List<T> listarPorData(String data) throws SQLException;
}
