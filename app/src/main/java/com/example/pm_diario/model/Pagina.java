package com.example.pm_diario.model;

public class Pagina extends Registro{
    private String titulo;

    public String getTitulo() {

        return titulo;
    }

    public void setTitulo(String titulo) {
        if(titulo.length() > 40){
            throw new IllegalArgumentException("O titulo não pode exceder 40 caracteres");
        }
        this.titulo = titulo;
    }
}
