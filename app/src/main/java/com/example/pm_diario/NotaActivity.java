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

import com.example.pm_diario.controller.NotaController;
import com.example.pm_diario.model.Emojis;
import com.example.pm_diario.model.Nota;
import com.example.pm_diario.persistence.NotaDao;


import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NotaActivity extends AppCompatActivity {

    private EditText etHora;
    private EditText etData;
    private EditText tmtexto;
    private Button btnSalvar;
    private Button btnEditar;
    private Button btnExcluir;
    private Spinner spEmoji;
    private Nota nota;
    private List<String> listaEmojis;

    private NotaController notaController = new NotaController(new NotaDao(this));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nota);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        spEmoji = findViewById(R.id.spEmoji);
        preencherEmoji();

        Button btnvoltar = findViewById(R.id.btnVoltar);
        etHora = findViewById(R.id.etHora);
        etData = findViewById(R.id.etData);
        tmtexto = findViewById(R.id.tmTexto);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnEditar = findViewById(R.id.btnEditar);
        btnExcluir = findViewById(R.id.btnExcluir);
        etHora.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm")));
        etData.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        btnvoltar.setOnClickListener(op -> voltar());
        btnSalvar.setOnClickListener(op -> salvar());
        btnExcluir.setOnClickListener(op -> excluir());
        btnEditar.setOnClickListener(op -> editar());

        Intent i = getIntent();
        Bundle bundle = i.getExtras();


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
            nota = notaController.buscar(id);
            etHora.setText(nota.getHora().format(DateTimeFormatter.ofPattern("hh:mm")));
            etData.setText(nota.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            posicaoSpinner(nota.getEmoji());
            tmtexto.setText(nota.getConteudo());


        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void editar() {
        try {
            if(spEmoji.getSelectedItemPosition() == 0){
                throw new IllegalArgumentException("Selecione um emoji");
            }
            nota.setEmoji(spEmoji.getSelectedItem().toString());
            nota.setData(LocalDate.parse(etData.getText().toString(),DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            nota.setConteudo(tmtexto.getText().toString());
            nota.setHora(LocalTime.parse(etHora.getText().toString(),DateTimeFormatter.ofPattern("HH:mm")));
            notaController.modificar(nota);
            voltar();
        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void excluir() {
        try {
            notaController.deletar(nota);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            voltar();
        }
    }

    private void salvar() {
        try {
            if(spEmoji.getSelectedItemPosition() == 0){
                throw new IllegalArgumentException("Selecione um emoji");
            }
            Nota n = new Nota();
            n.setHora(LocalTime.parse(etHora.getText().toString(),DateTimeFormatter.ofPattern("HH:mm")));
            n.setConteudo(tmtexto.getText().toString());
            n.setData(LocalDate.parse(etData.getText().toString(),DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            n.setEmoji(spEmoji.getSelectedItem().toString());
            notaController.inserir(n);
            voltar();
        } catch (SQLException e) {
            Toast.makeText(this, "Erro de conexao ao salvar", Toast.LENGTH_SHORT).show();
        }
        catch (IllegalArgumentException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            Toast.makeText(this, "Erro ao salvar", Toast.LENGTH_SHORT).show();
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