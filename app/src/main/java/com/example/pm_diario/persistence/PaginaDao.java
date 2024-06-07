package com.example.pm_diario.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pm_diario.model.Pagina;
import com.example.pm_diario.model.Registro;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaginaDao implements IPaginaDao, ICrudDao<Pagina> {
    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase db;

    public PaginaDao(Context context){
        this.context = context;
    }
    @Override
    public void insert(Pagina pagina) throws SQLException {

        ContentValues contextValuesRegistro = getRegistro(pagina);
        long id = db.insert("registro",null,contextValuesRegistro);

        ContentValues contextValuesPagina = getPagina(pagina);
        contextValuesPagina.put("registro_id",id);
        db.insert("pagina",null,contextValuesPagina);
    }

    @Override
    public void update(Pagina pagina) throws SQLException {
        ContentValues contentValues = getRegistro(pagina);
        db.update("registro",contentValues,"id = "+pagina.getId(),null);

        ContentValues contextValuesPagina = getPagina(pagina);
        db.update("pagina",contextValuesPagina,"registro_id = "+pagina.getId(),null);
    }

    @Override
    public void delete(Pagina pagina) throws SQLException {
        db.delete("pagina", "registro_id = "+ pagina.getId(), null);
        db.delete("registro", "id = "+ pagina.getId(), null);
    }

    @SuppressLint("Range")
    @Override
    public Pagina findById(int id) throws SQLException {
        String query = "SELECT r.*, p.* from registro as r join pagina as p on r.id = p.registro_id where id = "+id;
        Cursor cursor = db.rawQuery(query, null);
        Pagina pagina = null;
        if(cursor != null){
            cursor.moveToFirst();
        }
        if(!cursor.isAfterLast()){
            pagina = new Pagina();

            pagina.setId(cursor.getInt(cursor.getColumnIndex("registro_id")));
            pagina.setData(LocalDate.parse(cursor.getString(cursor.getColumnIndex("data"))));
            pagina.setTitulo(cursor.getString(cursor.getColumnIndex("titulo")));
            pagina.setEmoji(cursor.getString(cursor.getColumnIndex("emoji")));
            pagina.setConteudo(cursor.getString(cursor.getColumnIndex("conteudo")));

        }
        cursor.close();
        return pagina;
    }

    @SuppressLint("Range")
    @Override
    public List<Pagina> findAll() throws SQLException {
        String query = "SELECT r.*, p.* from registro as r join pagina as p on r.id = p.registro_id order by data DESC";
        Cursor cursor = db.rawQuery(query, null);
        List<Pagina> paginas = new ArrayList<>();
        if(cursor != null){
            cursor.moveToFirst();
        }
        while(!cursor.isAfterLast()){
            Pagina pagina = new Pagina();

            pagina.setId(cursor.getInt(cursor.getColumnIndex("registro_id")));
            pagina.setData(LocalDate.parse(cursor.getString(cursor.getColumnIndex("data"))));
            pagina.setTitulo(cursor.getString(cursor.getColumnIndex("titulo")));
            pagina.setEmoji(cursor.getString(cursor.getColumnIndex("emoji")));
            pagina.setConteudo(cursor.getString(cursor.getColumnIndex("conteudo")));
            paginas.add(pagina);
            cursor.moveToNext();
        }
        cursor.close();
        return paginas;
    }

    @Override
    public PaginaDao open() throws SQLException {
        gDao = new GenericDao(context);
        db = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }

    private ContentValues getPagina(Pagina p) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("titulo", p.getTitulo());
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
