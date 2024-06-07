package com.example.pm_diario;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pm_diario.model.Nota;
import com.example.pm_diario.model.Pagina;
import com.example.pm_diario.model.Registro;

import java.util.List;

public class RegistroAdapter extends RecyclerView.Adapter<RegistroAdapter.PersonViewHolder> {

    private final List<Registro> registroList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public RegistroAdapter(List<Registro> registroList, OnItemClickListener listener) {
        this.registroList = registroList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RegistroAdapter.PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_registro, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegistroAdapter.PersonViewHolder holder, int position) {
        Registro r = registroList.get(position);
        holder.tvConteudo.setText(r.getConteudo());
        holder.tvData.setText(String.valueOf(r.getData()));
        holder.tvEmoji.setText(r.getEmoji());
        if(r instanceof Pagina){
            holder.tvTitulo.setText(((Pagina) r).getTitulo());
        }
        if(r instanceof Nota){
            holder.tvTitulo.setText(String.valueOf(r.getData()));
            holder.tvTitulo.setGravity(Gravity.END);
            holder.tvData.setText(((Nota) r).getHora().toString());
        }
    }

    @Override
    public int getItemCount() {
        return registroList.size();
    }

    class PersonViewHolder extends RecyclerView.ViewHolder {
        TextView tvConteudo;
        TextView tvData;
        TextView tvEmoji;
        TextView tvTitulo;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvConteudo = itemView.findViewById(R.id.tvConteudo);
            tvData = itemView.findViewById(R.id.tvData);
            tvEmoji = itemView.findViewById(R.id.tvEmoji);

            tvTitulo = itemView.findViewById(R.id.tvTitulo);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
