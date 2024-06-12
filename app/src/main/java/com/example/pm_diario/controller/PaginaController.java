package com.example.pm_diario.controller;

import com.example.pm_diario.model.Pagina;
import com.example.pm_diario.persistence.PaginaDao;

import java.sql.SQLException;
import java.util.List;

public class PaginaController implements IController<Pagina>{
    private final PaginaDao pDao;

    public PaginaController(PaginaDao pDao){
        this.pDao = pDao;
    }
    @Override
    public void inserir(Pagina pagina) throws SQLException {
        if (pDao.open() == null){
            pDao.open();
        }
        pDao.insert(pagina);
        pDao.close();
    }

    @Override
    public void modificar(Pagina pagina) throws SQLException {
        if (pDao.open() == null){
            pDao.open();
        }
        pDao.update(pagina);
        pDao.close();
    }

    @Override
    public void deletar(Pagina pagina) throws SQLException {
        if (pDao.open() == null){
            pDao.open();
        }
        pDao.delete(pagina);
        pDao.close();
    }

    @Override
    public Pagina buscar(int id) throws SQLException {
        Pagina pagina;
        if (pDao.open() == null){
            pDao.open();
        }
        pagina = pDao.findById(id);
        pDao.close();
        return pagina;
    }

    @Override
    public List<Pagina> listar() throws SQLException {
        if (pDao.open() == null){
            pDao.open();
        }
        List<Pagina> paginas = pDao.findAll();
        pDao.close();
        return paginas;
    }

    @Override
    public List<Pagina> listarPorData(String data) throws SQLException {
        if (pDao.open() == null){
            pDao.open();
        }
        List<Pagina> paginas = pDao.findByData(data);
        pDao.close();
        return paginas;
    }

}
