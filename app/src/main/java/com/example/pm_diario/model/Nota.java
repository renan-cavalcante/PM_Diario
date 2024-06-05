package com.example.pm_diario.model;

import java.time.LocalTime;

public class Nota extends Registro{
    private LocalTime hora;

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    @Override
    public void setConteudo(String conteudo) throws IllegalArgumentException{
        if(conteudo.length() > 250){
            throw new IllegalArgumentException("A nota não pode exceder 250 caracteres");
        }
        super.setConteudo(conteudo);
    }
}
