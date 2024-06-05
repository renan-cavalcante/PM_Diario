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
    public Nota buscar(Nota nota) throws SQLException {
        if (nDao.open() == null){
            nDao.open();
        }
        nota = nDao.findById(nota.getId());
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
}
