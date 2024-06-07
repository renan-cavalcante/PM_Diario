package com.example.pm_diario;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.pm_diario.controller.NotaController;
import com.example.pm_diario.controller.PaginaController;
import com.example.pm_diario.model.Nota;
import com.example.pm_diario.model.Pagina;
import com.example.pm_diario.model.Registro;
import com.example.pm_diario.persistence.NotaDao;
import com.example.pm_diario.persistence.PaginaDao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DiarioFragment extends Fragment implements RegistroAdapter.OnItemClickListener{

    private View view;
    private RecyclerView rvRegistros;

    private List<Registro> registro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_diario, container, false);
        rvRegistros = view.findViewById(R.id.rvRegistros);
        try {
            List<Pagina> paginas = new PaginaController(new PaginaDao(view.getContext())).listar();
            List<Nota> notas = new NotaController(new NotaDao(view.getContext())).listar();
            registro = new ArrayList<>(paginas);
            registro.addAll(notas);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        RegistroAdapter adapter = new RegistroAdapter(registro,this);
        rvRegistros.setAdapter(adapter);
        rvRegistros.setLayoutManager(new LinearLayoutManager(view.getContext()));
        return view;
    }

    @Override
    public void onItemClick(int position) {
        Registro clickedItem = registro.get(position);
        Toast.makeText(view.getContext(), "Clicked: " + clickedItem, Toast.LENGTH_SHORT).show();
        //pegar o intem clicado e abrir ele
        //TO DO
    }
}