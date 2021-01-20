package com.tmidev.crudsqlite.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.tmidev.crudsqlite.R;
import com.tmidev.crudsqlite.util.AppUtil;

public class SplashActivity extends AppCompatActivity {

    private boolean isUsuarioConectado;
    private int idUsuario;
    private String emailUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        restaurarPreferenciasDeLogin();

        iniciarApp();

    }

    private void iniciarApp() {
        new Handler().postDelayed(() -> {
            Intent iTarget;
            if (isUsuarioConectado && idUsuario > 0 && !emailUsuario.equals("")) {
                iTarget = new Intent(SplashActivity.this, MainActivity.class);
            } else {
                iTarget = new Intent(SplashActivity.this, LoginActivity.class);
            }
            startActivity(iTarget);
            finish();
        }, AppUtil.TIME_SPLASH);
    }

    private void restaurarPreferenciasDeLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences(AppUtil.APP_PREF, MODE_PRIVATE);
        isUsuarioConectado = sharedPreferences.getBoolean("isUsuarioConectado", false);
        idUsuario = sharedPreferences.getInt("idUsuario", -1);
        emailUsuario = sharedPreferences.getString("emailUsuario", "");
    }

}