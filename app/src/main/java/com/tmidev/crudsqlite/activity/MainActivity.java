package com.tmidev.crudsqlite.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.tmidev.crudsqlite.R;

public class MainActivity extends AppCompatActivity {

    Button buttonListaUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciarObj();

        buttonListaUsuarios.setOnClickListener(v -> {
            Intent iTarget = new Intent(MainActivity.this, ListaUsuariosActivity.class);
            startActivity(iTarget);
        });

    }

    private void iniciarObj() {
        buttonListaUsuarios = findViewById(R.id.buttonListaUsuarios);

        // toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.appName);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuConfig) {
            Intent iTarget = new Intent(getApplicationContext(), ConfigActivity.class);
            MainActivity.this.startActivity(iTarget);
        }

        if (item.getItemId() == R.id.menuSair) {
            Intent iTarget = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(iTarget);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}