package com.example.pm_diario;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pm_diario.controller.NotaController;
import com.example.pm_diario.controller.PaginaController;
import com.example.pm_diario.model.Nota;
import com.example.pm_diario.model.Pagina;
import com.example.pm_diario.persistence.NotaDao;
import com.example.pm_diario.persistence.PaginaDao;
import com.google.android.material.button.MaterialButton;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class NotaActivity extends AppCompatActivity {


    private MaterialButton imageFilterButton;
    private EditText etHora;
    private EditText etData;
    private EditText tmtexto;
    private Button btnSalvar;

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

        imageFilterButton = findViewById(R.id.imageFilterButton);
        imageFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });

        Button btnvoltar = findViewById(R.id.btnVoltar);
        etHora = findViewById(R.id.etHora);
        etData = findViewById(R.id.etData);
        tmtexto = findViewById(R.id.tmTexto);
        btnSalvar = findViewById(R.id.btnSalvar);

        btnvoltar.setOnClickListener(op -> voltar());
        btnSalvar.setOnClickListener(op -> salvar());
    }

    private void salvar() {
        try {
            Nota n = new Nota();
            n.setHora(LocalTime.parse(etHora.getText().toString()));
            n.setConteudo(tmtexto.getText().toString());
            n.setData(LocalDate.parse(etData.getText().toString()));
            n.setEmoji(imageFilterButton.getText().toString());
            notaController.inserir(n);
            voltar();
        } catch (SQLException e) {
            Toast.makeText(this, "Erro de conexao ao salvar", Toast.LENGTH_SHORT).show();
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

    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_emojis, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String emoji = item.getTitle().toString();
                imageFilterButton.setText(emoji);
                imageFilterButton.setIcon(null); // Remove the icon// Make the button not clickable
                Toast.makeText(NotaActivity.this, "Emoji selecionado: " + emoji, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        popup.show();
    }
}