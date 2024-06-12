package com.example.pm_diario;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pm_diario.controller.PaginaController;
import com.example.pm_diario.model.Emojis;
import com.example.pm_diario.model.Pagina;
import com.example.pm_diario.persistence.PaginaDao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class PaginaActivity extends AppCompatActivity {


    private EditText etTitulo;
    private EditText etData;
    private EditText tmtexto;
    private Button btnSalvar;
    private Button btnEditar;
    private Button btnExcluir;
    private Spinner spEmoji;
    private Pagina pagina;
    private List<String> listaEmojis;
    private PaginaController paginaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pagina);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




        paginaController = new PaginaController(new PaginaDao(this));

        Button btnvoltar = findViewById(R.id.btnVoltar);
        etTitulo = findViewById(R.id.edTitulo);
        etData = findViewById(R.id.etData);
        tmtexto = findViewById(R.id.tmTexto);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnEditar = findViewById(R.id.btnEditar);
        btnExcluir = findViewById(R.id.btnExcluir);
        spEmoji = findViewById(R.id.spEmoji);
        preencherEmoji();

        etData.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        btnvoltar.setOnClickListener(op -> voltar());
        btnSalvar.setOnClickListener(op -> salvar());
        btnExcluir.setOnClickListener(op -> excluir());
        btnEditar.setOnClickListener(op -> editar());

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        Toast.makeText(this, "Carregour", Toast.LENGTH_LONG).show();



        if(bundle != null){
            carregar(Integer.parseInt(bundle.getString("id")));
            btnSalvar.setClickable(false);
            btnSalvar.setBackgroundTintList((ColorStateList.valueOf(getResources().getColor(R.color.black_op))));
        }else{
            btnExcluir.setClickable(false);
            btnExcluir.setVisibility(View.INVISIBLE);
            btnEditar.setClickable(false);
            btnEditar.setVisibility(View.INVISIBLE);
        }


    }

    private void carregar(int id) {

        try {
           pagina = paginaController.buscar(id);
           etTitulo.setText(pagina.getTitulo());
           etData.setText(pagina.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            posicaoSpinner(pagina.getEmoji());
           tmtexto.setText(pagina.getConteudo());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            Toast.makeText(PaginaActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void editar() {
        try {
            pagina.setEmoji(spEmoji.getSelectedItem().toString());
            pagina.setData(LocalDate.parse(etData.getText().toString(),DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            pagina.setConteudo(tmtexto.getText().toString());
            pagina.setTitulo(etTitulo.getText().toString());
            paginaController.modificar(pagina);
            voltar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            Toast.makeText(PaginaActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void excluir() {
        try {
            paginaController.deletar(pagina);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            voltar();
        }
    }

    private void salvar() {
        try {
            Pagina p = new Pagina();
            p.setTitulo(etTitulo.getText().toString());
            p.setConteudo(tmtexto.getText().toString());

            p.setData(LocalDate.parse(etData.getText().toString(),DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            p.setEmoji(spEmoji.getSelectedItem().toString());

            paginaController.inserir(p);

            voltar();
        } catch (SQLException e) {
            Toast.makeText(PaginaActivity.this, "Erro de conexao ao salvar", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            Toast.makeText(PaginaActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void voltar() {
        Intent i = new Intent(this, MainActivity.class);
        this.startActivity(i);
        this.finish();
    }


    private void preencherEmoji(){
        listaEmojis = Emojis.obterList();
        ArrayAdapter ad = new ArrayAdapter(this,android.R.layout.simple_spinner_item,listaEmojis);
        spEmoji.setAdapter(ad);
    }

    private void posicaoSpinner(String sp){
        int cont = 0;
        for(String emoji: listaEmojis){
            if(emoji.equals(sp)){
                spEmoji.setSelection(cont);

            }else {
                cont++;
            }
        }
        if(cont > listaEmojis.size()){
            spEmoji.setSelection(0);
        }
    }
}