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

import com.example.pm_diario.model.Pagina;
import com.example.pm_diario.model.Registro;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DiarioFragment extends Fragment implements RegistroAdapter.OnItemClickListener{

    private View view;
    private RecyclerView rvRegistros;

    private List<Registro> s;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_diario, container, false);
        rvRegistros = view.findViewById(R.id.rvRegistros);
        s = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            Registro r = new Pagina();
            r.setConteudo("conteudo " + i);
            r.setData(LocalDate.now());
            r.setEmoji("emoji");
            r.setId(i);
            s.add(r);
        }


        RegistroAdapter adapter = new RegistroAdapter(s,this);
        rvRegistros.setAdapter(adapter);
        rvRegistros.setLayoutManager(new LinearLayoutManager(view.getContext()));
        return view;
    }

    @Override
    public void onItemClick(int position) {
        Registro clickedItem = s.get(position);
        Toast.makeText(view.getContext(), "Clicked: " + clickedItem, Toast.LENGTH_SHORT).show();
        //pegar o intem clicado e abrir ele
        //TO DO
    }
}