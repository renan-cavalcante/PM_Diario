package com.example.pm_diario;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

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

public class CalendarioFragment extends Fragment implements RegistroAdapter.OnItemClickListener {

    private View view;
    private RecyclerView rvRegistros;
    private List<Registro> registro;
    RegistroAdapter adapter;
    CalendarView calendarView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calendario, container, false);
        rvRegistros = view.findViewById(R.id.rvRegistros);
        calendarView = view.findViewById(R.id.calendario);


        listenCalendario();

        return view;
    }


    public void listenCalendario(){
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String mes = String.valueOf(month +1);
                if(month +1 < 10){
                    mes = 0+mes;
                }

                String selectedDate = year + "-" + mes + "-" + dayOfMonth;
                addRegistroLista(selectedDate);
                Toast.makeText(view.getContext(), "Data selecionada: " + selectedDate, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addRegistroLista(String data){
        try {
            List<Pagina> paginas = new PaginaController(new PaginaDao(view.getContext())).listarPorData(data);
            List<Nota> notas = new NotaController(new NotaDao(view.getContext())).listarPorData(data);
            registro = new ArrayList<>(paginas);
            registro.addAll(notas);
            Registro.ordenar(registro);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        adapter = new RegistroAdapter(registro,this);
        rvRegistros.setAdapter(adapter);
        rvRegistros.setLayoutManager(new LinearLayoutManager(view.getContext()));
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