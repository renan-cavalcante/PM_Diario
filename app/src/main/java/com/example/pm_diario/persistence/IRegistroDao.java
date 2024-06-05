package com.example.pm_diario.persistence;

import java.sql.SQLException;

public interface IRegistroDao {
    RegistroDao open()  throws SQLException;
    void close();

}
