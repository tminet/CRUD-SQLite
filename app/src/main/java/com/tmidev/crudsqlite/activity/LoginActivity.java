package com.tmidev.crudsqlite.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.tmidev.crudsqlite.R;
import com.tmidev.crudsqlite.controller.UsuarioController;
import com.tmidev.crudsqlite.model.UsuarioModel;
import com.tmidev.crudsqlite.util.AppUtil;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout layoutEmail, layoutSenha;
    EditText editTextEmail, editTextSenha;
    CheckBox checkBoxManterConectado;
    Button buttonEntrar;

    private boolean isManterConectado;

    SharedPreferences sharedPreferences;

    UsuarioModel usuarioModel;
    UsuarioController usuarioController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iniciarObj();

        buttonEntrar.setOnClickListener(v -> {
            if (verificarFormulario()) {
                usuarioModel = validarLogin();
                if (usuarioModel.getId() > 0) {
                    salvarPreferenciasDeLogin();
                    Intent iHome = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(iHome);
                    finish();
                } else {
                    Toast.makeText(this, "Dados invalidos...", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void iniciarObj() {
        layoutEmail = findViewById(R.id.layoutEmail);
        layoutSenha = findViewById(R.id.layoutSenha);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSenha = findViewById(R.id.editTextSenha);
        checkBoxManterConectado = findViewById(R.id.checkBoxManterConectado);
        buttonEntrar = findViewById(R.id.buttonEntrar);

        usuarioModel = new UsuarioModel();
        usuarioController = new UsuarioController(this);

        verificarFormularioOnFocusChange();

        salvarResetDoLogin();

    }

    // verificar se os campos estão vazios (ativa apenas apos ser chamado no click)
    private boolean verificarFormulario() {
        boolean isFormularioCompleto = true;
        if (TextUtils.isEmpty(editTextSenha.getText().toString().trim())) {
            layoutSenha.setError("Digite sua senha");
            layoutSenha.requestFocus();
            isFormularioCompleto = false;
        }
        if (TextUtils.isEmpty(editTextEmail.getText().toString().trim())) {
            layoutEmail.setError("Digite seu email");
            layoutEmail.requestFocus();
            isFormularioCompleto = false;
        }
        return isFormularioCompleto;
    }

    // verificar se os campos possuem conteudo (ativa apos o campo perder o focus -- apenas para efeito visual)
    private void verificarFormularioOnFocusChange() {
        editTextSenha.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                if (TextUtils.isEmpty(editTextSenha.getText().toString().trim())) {
                    layoutSenha.setError("Digite sua senha");
                } else {
                    layoutSenha.setError(null);
                }
            }
        });
        editTextEmail.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                if (TextUtils.isEmpty(editTextEmail.getText().toString().trim())) {
                    layoutEmail.setError("Digite seu email");
                } else {
                    layoutEmail.setError(null);
                }
            }
        });
        // listener para verificar quando algo é digitado
        editTextSenha.addTextChangedListener(new AppUtil.TextChangedListener<EditText>(editTextSenha) {
            @Override
            public void onTextChanged(EditText target, Editable s) {
                layoutSenha.setError(null);
            }
        });
        editTextEmail.addTextChangedListener(new AppUtil.TextChangedListener<EditText>(editTextEmail) {
            @Override
            public void onTextChanged(EditText target, Editable s) {
                layoutEmail.setError(null);
            }
        });
    }

    public void manterConectado(View view) {
        isManterConectado = checkBoxManterConectado.isChecked();
    }

    private UsuarioModel validarLogin() {
        usuarioModel.setEmail(editTextEmail.getText().toString());
        usuarioModel.setSenha(AppUtil.gerarMD5Hash(editTextSenha.getText().toString()));
        return usuarioController.autenticarUsuario(usuarioModel);
    }

    public void criarConta(View view) {
        Intent iCriarConta = new Intent(LoginActivity.this, CriarContaActivity.class);
        startActivity(iCriarConta);
    }

    // só chama quando os dados de login são validos
    private void salvarPreferenciasDeLogin() {
        sharedPreferences = getSharedPreferences(AppUtil.APP_PREF, MODE_PRIVATE);
        SharedPreferences.Editor salvarDados = sharedPreferences.edit();
        salvarDados.putBoolean("isUsuarioConectado", isManterConectado);
        salvarDados.putInt("idUsuario", usuarioModel.getId());
        salvarDados.putString("emailUsuario", usuarioModel.getEmail());
        salvarDados.apply();
    }

    // caso entre na tela de login, tirar o id do usuario e a autenticação automatica
    private void salvarResetDoLogin() {
        sharedPreferences = getSharedPreferences(AppUtil.APP_PREF, MODE_PRIVATE);
        SharedPreferences.Editor salvarDados = sharedPreferences.edit();
        salvarDados.putBoolean("isUsuarioConectado", false);
        salvarDados.putInt("idUsuario", -1);
        salvarDados.putString("emailUsuario", "");
        salvarDados.apply();
    }

}