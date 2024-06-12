package com.example.pm_diario.model;

import java.util.ArrayList;
import java.util.List;

public enum Emojis {
     DEFAULT("\uD83E\uDEE5"),FELIZ("😀"), RINDO("😂"), APAIXONANDO("😍"), TRISTE("🙁");

    private final String emoji;

    Emojis(String emoji) {
        this.emoji = emoji;
    }

    @Override
    public String toString() {
        return emoji;
    }

    public static List<String> obterList(){
        List<String> list = new ArrayList<>();
        for (Emojis emoji : Emojis.values()) {
            list.add(emoji.toString());
        }
        return list;
    }
}
