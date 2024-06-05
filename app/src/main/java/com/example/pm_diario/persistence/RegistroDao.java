package com.example.pm_diario.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

import com.example.pm_diario.model.Registro;

import java.sql.SQLException;
import java.util.List;

public class RegistroDao implements IRegistroDao, ICrudDao<Registro>{

    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase db;

    public RegistroDao(Context context){
        this.context = context;
    }

    @Override
    public void insert(Registro registro) throws SQLException {
        ContentValues contextValues = getRegistro(registro);
        db.insert("registro",null,contextValues);
    }

    @Override
    public void update(Registro registro) throws SQLException {
        ContentValues contentValues = getRegistro(registro);
        db.update("registro",contentValues,"id = "+registro.getId(),null);
    }

    @Override
    public void delete(Registro registro) throws SQLException {
        db.delete("registro", "id = "+ registro.getId(), null);
    }

    @Override
    public Registro findById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Registro> findAll() throws SQLException {
        return null;
    }

    @Override
    public RegistroDao open() throws SQLException {
        return null;
    }

    @Override
    public void close() {

    }

    private ContentValues getRegistro(Registro r) {
        ContentValues contentValues = new ContentValues();
        return contentValues;
    }
}
