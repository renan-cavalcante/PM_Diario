package com.example.pm_diario.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Registro {
    private int id;
    private LocalDate data;
    private String emoji;
    private String conteudo;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}
