package com.example.pm_diario.model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Registro implements Comparable<Registro>{
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

    @Override
    public int compareTo(Registro r) {
        return r.getData().compareTo(this.data);
    }

    public static void ordenar(List<Registro> r){
        Collections.sort(r, new Comparator<Registro>(){
            public int compare(Registro r1, Registro r2){
                int ret = r1.compareTo(r2);
                if(ret != 0){
                    return  ret;
                }
                if(r1 instanceof Nota && r2 instanceof Nota){
                    return  ((Nota) r2).getHora().compareTo(((Nota) r1).getHora());
                }

                if(r1 instanceof  Pagina && r2 instanceof  Nota){
                    return -1;
                }
                return 1;
            }
        });
    }
}
