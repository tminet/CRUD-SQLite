package com.tmidev.crudsqlite.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.tmidev.crudsqlite.R;
import com.tmidev.crudsqlite.adapter.UsuarioAdapter;
import com.tmidev.crudsqlite.controller.UsuarioController;
import com.tmidev.crudsqlite.model.UsuarioModel;

import java.util.List;
import java.util.Objects;

public class ListaUsuariosActivity extends AppCompatActivity {

    RecyclerView recyclerViewUsuarios;

    UsuarioAdapter usuarioAdapter;
    UsuarioController usuarioController;
    List<UsuarioModel> usuariosList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);

        iniciarObj();

    }

    private void iniciarObj() {
        recyclerViewUsuarios = findViewById(R.id.recyclerViewUsuarios);

        // toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Lista de Usuarios");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        usuarioController = new UsuarioController(getApplicationContext());
        usuariosList = usuarioController.listarUsuarios();

        usuarioAdapter = new UsuarioAdapter(usuariosList, getApplicationContext());
        recyclerViewUsuarios.setAdapter(usuarioAdapter);
        recyclerViewUsuarios.setLayoutManager(new LinearLayoutManager(this));

    }

}