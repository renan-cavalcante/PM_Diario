package com.example.pm_diario.persistence;

import java.sql.SQLException;

public interface IPaginaDao {
    PaginaDao open()  throws SQLException;
    void close();
}
