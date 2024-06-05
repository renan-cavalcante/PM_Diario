package com.example.pm_diario.persistence;

import java.sql.SQLException;

public interface INotaDao {
    NotaDao open()  throws SQLException;
    void close();
}
