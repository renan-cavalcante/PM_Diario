package com.example.pm_diario;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pm_diario.controller.NotaController;
import com.example.pm_diario.controller.PaginaController;
import com.example.pm_diario.model.Nota;
import com.example.pm_diario.model.Pagina;
import com.example.pm_diario.model.Registro;
import com.example.pm_diario.persistence.NotaDao;
import com.example.pm_diario.persistence.PaginaDao;

import java.sql.SQLException;
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
            Registro.ordenar(registro);
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
        Registro r = registro.get(position);
        if (r instanceof Pagina){
            Intent i = new Intent(view.getContext(), PaginaActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("id",String.valueOf(r.getId()));
            i.putExtras(bundle);
            view.getContext().startActivity(i);
            onDestroyView();
        }
        if (r instanceof Nota){
            Intent i = new Intent(view.getContext(), NotaActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("id",String.valueOf(r.getId()));
            i.putExtras(bundle);
            view.getContext().startActivity(i);
            onDestroyView();
        }

    }
}