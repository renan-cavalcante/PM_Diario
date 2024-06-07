package com.example.pm_diario.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class GenericDao extends SQLiteOpenHelper {
    private static final String DATABASE = "DIARIO.DB";
    private static final int DATABASE_VER = 5;

    private static final String CREATE_TABLE_REGISTRO =
            "CREATE TABLE registro (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "data VARCHAR(10) NOT NULL, " +
                    "emoji VARCHAR(1) NOT NULL, " +
                    "conteudo TEXT NOT NULL );";

    private static final String CREATE_TABLE_NOTA =
            "CREATE TABLE nota (" +
                    "registro_id INTEGER PRIMARY KEY , " +
                    "hora VARCHAR(8) NOT NULL," +
                    "FOREIGN KEY (registro_id) REFERENCES regitro(id) );";

    private static final String CREATE_TABLE_PAGINA=
            "CREATE TABLE pagina (" +
                    "registro_id INTEGER PRIMARY KEY, " +
                    "titulo VARCHAR(40) NOT NULL," +
                    "FOREIGN KEY (registro_id) REFERENCES regitro(id) );";

    public GenericDao(@Nullable Context context) {
        super(context, DATABASE,null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_REGISTRO);
        db.execSQL(CREATE_TABLE_NOTA);
        db.execSQL(CREATE_TABLE_PAGINA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > oldVersion){
            db.execSQL("DROP TABLE IF EXISTS registro");
            db.execSQL("DROP TABLE IF EXISTS nota");
            db.execSQL("DROP TABLE IF EXISTS pagina");
            onCreate(db);
        }
    }
}
