package com.tmidev.crudsqlite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tmidev.crudsqlite.R;
import com.tmidev.crudsqlite.model.UsuarioModel;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.ViewHolder> {

    private List<UsuarioModel> adpUsuario; // Lista de Usuarios do Adapter
    private Context adpContext; // contexto do Adapter

    // construtor que recebe a lista e o contexto
    public UsuarioAdapter(List<UsuarioModel> adpUsuario, Context adpContext) {
        this.adpUsuario = adpUsuario;
        this.adpContext = adpContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // declarar os objs da lista
        public TextView textViewId, textViewNome, textViewEmail, textViewData;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // instanciar os objs
            textViewId = itemView.findViewById(R.id.textViewId);
            textViewNome = itemView.findViewById(R.id.textViewNome);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewData = itemView.findViewById(R.id.textViewData);

            itemView.setOnClickListener(this); // para usar o onclick abaixo
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition(); // pegar a posição/ordem do item
            UsuarioModel usuarioSelecionado = adpUsuario.get(position);
            if (position != RecyclerView.NO_POSITION) { // verifica se o item, por algum motivo, não foi removido da lista
                // todo alguma coisa ~
                Toast.makeText(adpContext, "ID: " + usuarioSelecionado.getId(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context); // contexto do recycleView

        View viewUsuario = layoutInflater.inflate(R.layout.adapter_usuario, parent, false);

        return new ViewHolder(viewUsuario);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioAdapter.ViewHolder holder, int position) {
        // vincular os dados de cada usuario em itens do adapter xml
        UsuarioModel objUsuario = adpUsuario.get(position);

        holder.textViewId.setText(String.valueOf(objUsuario.getId()));
        holder.textViewNome.setText(objUsuario.getNome());
        holder.textViewEmail.setText(objUsuario.getEmail());
        holder.textViewData.setText(objUsuario.getData());

    }

    @Override
    public int getItemCount() {
        return adpUsuario.size();
    }

}