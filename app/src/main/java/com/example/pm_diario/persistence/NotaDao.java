package com.example.pm_diario.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

import com.example.pm_diario.model.Nota;
import com.example.pm_diario.model.Registro;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class NotaDao implements INotaDao, ICrudDao<Nota>{

    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase db;

    public NotaDao(Context context){
        this.context = context;
    }
    @Override
    public void insert(Nota nota) throws SQLException {
        ContentValues contextValuesRegistro = getRegistro(nota);
        long id = db.insert("registro",null,contextValuesRegistro);

        ContentValues contextValuesNota = getNota(nota);
        contextValuesNota.put("registro_id",id);
        db.insert("nota",null,contextValuesNota);
    }

    @Override
    public void update(Nota nota) throws SQLException {
        ContentValues contentValues = getRegistro(nota);
        db.update("registro",contentValues,"id = "+nota.getId(),null);

        ContentValues contextValuesNota = getNota(nota);
        db.update("nota",contextValuesNota,"registro_id = "+nota.getId(),null);
    }

    @Override
    public void delete(Nota nota) throws SQLException {
        db.delete("nota", "registro_id = "+ nota.getId(), null);
        db.delete("registro", "id = "+ nota.getId(), null);
    }

    @SuppressLint("Range")
    @Override
    public Nota findById(int id) throws SQLException {
        String query = "SELECT r.*, n.* from registro as r join nota as n on r.id = n.registro_id where id = "+id;
        Cursor cursor = db.rawQuery(query, null);
        Nota nota = null;
        if(cursor != null){
            cursor.moveToFirst();
        }
        if(!cursor.isAfterLast()){
            nota = new Nota();

            nota.setId(cursor.getInt(cursor.getColumnIndex("registro_id")));
            nota.setData(LocalDate.parse(cursor.getString(cursor.getColumnIndex("data"))));
            nota.setHora(LocalTime.parse(cursor.getString(cursor.getColumnIndex("hora"))));
            nota.setEmoji(cursor.getString(cursor.getColumnIndex("emoji")));
            nota.setConteudo(cursor.getString(cursor.getColumnIndex("conteudo")));

        }
        cursor.close();
        return nota;
    }

    @SuppressLint("Range")
    @Override
    public List<Nota> findAll() throws SQLException {
        String query = "SELECT r.*, n.* from registro as r join nota as n on r.id = n.registro_id ORDER BY data DESC";
        Cursor cursor = db.rawQuery(query, null);
        List<Nota> notas = new ArrayList<>();
        if(cursor != null){
            cursor.moveToFirst();
        }
        while(!cursor.isAfterLast()){
            Nota nota = new Nota();

            nota.setId(cursor.getInt(cursor.getColumnIndex("registro_id")));
            nota.setData(LocalDate.parse(cursor.getString(cursor.getColumnIndex("data"))));
            nota.setHora(LocalTime.parse(cursor.getString(cursor.getColumnIndex("hora"))));
            nota.setEmoji(cursor.getString(cursor.getColumnIndex("emoji")));
            nota.setConteudo(cursor.getString(cursor.getColumnIndex("conteudo")));
            notas.add(nota);
            cursor.moveToNext();
        }
        cursor.close();
        return notas;
    }

    @SuppressLint("Range")
    @Override
    public List<Nota> findByData(String data) throws SQLException {
        String query = "SELECT r.*, n.* from registro as r join nota as n on r.id = n.registro_id  where r.data = '"+data.trim()+"' ORDER BY data DESC";
        Cursor cursor = db.rawQuery(query, null);
        List<Nota> notas = new ArrayList<>();
        if(cursor != null){
            cursor.moveToFirst();
        }
        while(!cursor.isAfterLast()){
            Nota nota = new Nota();

            nota.setId(cursor.getInt(cursor.getColumnIndex("registro_id")));
            nota.setData(LocalDate.parse(cursor.getString(cursor.getColumnIndex("data"))));
            nota.setHora(LocalTime.parse(cursor.getString(cursor.getColumnIndex("hora"))));
            nota.setEmoji(cursor.getString(cursor.getColumnIndex("emoji")));
            nota.setConteudo(cursor.getString(cursor.getColumnIndex("conteudo")));
            notas.add(nota);
            cursor.moveToNext();
        }
        cursor.close();
        return notas;
    }

    @Override
    public NotaDao open() throws SQLException {
        gDao = new GenericDao(context);
        db = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }

    private ContentValues getNota(Nota n) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("hora", n.getHora().toString());
        return contentValues;
    }

    private ContentValues getRegistro(Registro r) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("conteudo", r.getConteudo());
        contentValues.put("emoji", r.getEmoji());
        contentValues.put("data", r.getData().toString());
        return contentValues;
    }
}
