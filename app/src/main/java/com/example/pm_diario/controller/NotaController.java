package com.example.pm_diario.controller;

import com.example.pm_diario.model.Nota;
import com.example.pm_diario.persistence.NotaDao;

import java.sql.SQLException;
import java.util.List;

public class NotaController  implements  IController<Nota>{
    private final NotaDao nDao;

    public NotaController(NotaDao nDao){
        this.nDao = nDao;
    }
    @Override
    public void inserir(Nota nota) throws SQLException {
        if (nDao.open() == null){
            nDao.open();
        }
        nDao.insert(nota);
        nDao.close();
    }

    @Override
    public void modificar(Nota nota) throws SQLException {
        if (nDao.open() == null){
            nDao.open();
        }
        nDao.update(nota);
        nDao.close();
    }

    @Override
    public void deletar(Nota nota) throws SQLException {
        if (nDao.open() == null){
            nDao.open();
        }
        nDao.delete(nota);
        nDao.close();
    }

    @Override
    public Nota buscar(int id) throws SQLException {
        Nota nota;
        if (nDao.open() == null){
            nDao.open();
        }
        nota = nDao.findById(id);
        nDao.close();
        return nota;
    }

    @Override
    public List<Nota> listar() throws SQLException {
        if (nDao.open() == null){
            nDao.open();
        }
        List<Nota> notas = nDao.findAll();
        nDao.close();
        return notas;
    }

    public List<Nota> listarPorData(String data) throws SQLException {
        if (nDao.open() == null){
            nDao.open();
        }
        List<Nota> notas = nDao.findByData(data);
        nDao.close();
        return notas;
    }
}
