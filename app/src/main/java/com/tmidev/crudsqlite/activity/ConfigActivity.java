package com.tmidev.crudsqlite.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.tmidev.crudsqlite.R;
import com.tmidev.crudsqlite.controller.UsuarioController;
import com.tmidev.crudsqlite.model.UsuarioModel;
import com.tmidev.crudsqlite.util.AppUtil;

import java.util.Objects;

public class ConfigActivity extends AppCompatActivity {

    TextInputLayout layoutId, layoutNome, layoutEmail, layoutSenha, layoutData;
    EditText editTextId, editTextNome, editTextEmail, editTextSenha, editTextData;
    Button buttonEditar, buttonCancelar, buttonSalvar, buttonExcluir;

    private int idUsuario;

    SharedPreferences sharedPreferences;

    UsuarioModel usuario;
    UsuarioController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        iniciarObj();

    }

    private void iniciarObj() {
        layoutId = findViewById(R.id.layoutId);
        layoutNome = findViewById(R.id.layoutNome);
        layoutEmail = findViewById(R.id.layoutEmail);
        layoutSenha = findViewById(R.id.layoutSenha);
        layoutData = findViewById(R.id.layoutData);
        editTextId = findViewById(R.id.editTextId);
        editTextNome = findViewById(R.id.editTextNome);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSenha = findViewById(R.id.editTextSenha);
        editTextData = findViewById(R.id.editTextData);
        buttonEditar = findViewById(R.id.buttonEditar);
        buttonSalvar = findViewById(R.id.buttonSalvar);
        buttonExcluir = findViewById(R.id.buttonExcluir);
        buttonCancelar = findViewById(R.id.buttonCancelar);

        // toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Configurações");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        usuario = new UsuarioModel();
        controller = new UsuarioController(this);

        restaurarPreferenciasIdUsuario();
        resgatarDadosUsuario();

        verificarFormularioOnFocusChange();

    }

    private void resgatarDadosUsuario() {
        usuario = controller.dadosUsuario(idUsuario);
        editTextId.setText(String.valueOf(idUsuario));
        editTextNome.setText(usuario.getNome());
        editTextEmail.setText(usuario.getEmail());
        editTextData.setText(usuario.getData());
        editTextSenha.setText("000000000000");

    }

    public void editar(View view) {
        editTextNome.setEnabled(true);
        editTextEmail.setEnabled(true);
        editTextSenha.setEnabled(true);

        buttonEditar.setEnabled(false);
        buttonEditar.setVisibility(View.GONE);

        buttonCancelar.setVisibility(View.VISIBLE);
        buttonCancelar.setEnabled(true);

        buttonExcluir.setEnabled(false);
        buttonExcluir.setVisibility(View.GONE);

        buttonSalvar.setVisibility(View.VISIBLE);
        buttonSalvar.setEnabled(true);

        editTextSenha.setText("");
        layoutSenha.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
    }

    public void cancelar(View view) {
        resgatarDadosUsuario();

        editTextNome.setEnabled(false);
        editTextEmail.setEnabled(false);
        editTextSenha.setEnabled(false);

        buttonCancelar.setEnabled(false);
        buttonCancelar.setVisibility(View.GONE);

        buttonEditar.setVisibility(View.VISIBLE);
        buttonEditar.setEnabled(true);

        buttonSalvar.setEnabled(false);
        buttonSalvar.setVisibility(View.GONE);

        buttonExcluir.setVisibility(View.VISIBLE);
        buttonExcluir.setEnabled(true);

        editTextNome.setText(usuario.getNome());
        editTextEmail.setText(usuario.getEmail());

        layoutSenha.setEndIconMode(TextInputLayout.END_ICON_NONE);
        editTextSenha.setText("000000000000");
    }

    public void salvar(View view) {
        if (verificarFormulario()) {
            usuario.setNome(editTextNome.getText().toString());
            usuario.setEmail(editTextEmail.getText().toString());
            usuario.setSenha(editTextSenha.getText().toString());
            if (controller.verificarDisponibiliadeEmail(usuario)) {
                controller.alterarDadosUsuario(usuario);
                salvarSharedPrefDadosUsuario();

                editTextNome.setEnabled(false);
                editTextEmail.setEnabled(false);
                editTextSenha.setEnabled(false);

                buttonCancelar.setEnabled(false);
                buttonCancelar.setVisibility(View.GONE);

                buttonEditar.setVisibility(View.VISIBLE);
                buttonEditar.setEnabled(true);

                buttonSalvar.setEnabled(false);
                buttonSalvar.setVisibility(View.GONE);

                buttonExcluir.setVisibility(View.VISIBLE);
                buttonExcluir.setEnabled(true);

                layoutSenha.setEndIconMode(TextInputLayout.END_ICON_NONE);
                editTextSenha.setText("000000000000");
            } else {
                Toast.makeText(this, "Email indisponivel.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void excluir(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Excluir sua conta? Essa ação não pode ser revertida.")
                .setPositiveButton("Sim", (dialog, id) -> {
                    controller.excluirUsuario(usuario);
                    Intent iTarget = new Intent(ConfigActivity.this, LoginActivity.class);
                    startActivity(iTarget);
                    finish();
                })
                .setNegativeButton("Não", (dialog, id) -> {
                    dialog.cancel();
                }).show();
    }

    private boolean verificarFormulario() {
        boolean isFormularioCompleto = true;
        String nome = editTextNome.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String senha = editTextSenha.getText().toString().trim();

        if (TextUtils.isEmpty(senha)) {
            layoutSenha.setError("Informe uma senha");
            layoutSenha.requestFocus();
            isFormularioCompleto = false;
        }
        if (TextUtils.isEmpty(email)) {
            layoutEmail.setError("Informe seu email");
            layoutEmail.requestFocus();
            isFormularioCompleto = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            layoutEmail.setError("Email invalido");
            layoutEmail.requestFocus();
            isFormularioCompleto = false;
        }
        if (TextUtils.isEmpty(nome)) {
            layoutNome.setError("Informe seu nome");
            layoutNome.requestFocus();
            isFormularioCompleto = false;
        } else if (!AppUtil.nomePattern(nome)) {
            layoutNome.setError("Nome invalido");
            layoutNome.requestFocus();
            isFormularioCompleto = false;
        }

        return isFormularioCompleto;
    }

    private void verificarFormularioOnFocusChange() {
        editTextSenha.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                if (TextUtils.isEmpty(editTextSenha.getText().toString())) {
                    layoutSenha.setError("Informe uma senha");
                } else {
                    layoutSenha.setError(null);
                }
            }
        });
        editTextEmail.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                if (TextUtils.isEmpty(editTextEmail.getText().toString())) {
                    layoutEmail.setError("Informe seu email");
                } else {
                    layoutEmail.setError(null);
                }
            }
        });
        editTextNome.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                if (TextUtils.isEmpty(editTextNome.getText().toString())) {
                    layoutNome.setError("Informe seu nome");
                } else {
                    layoutNome.setError(null);
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
        editTextNome.addTextChangedListener(new AppUtil.TextChangedListener<EditText>(editTextNome) {
            @Override
            public void onTextChanged(EditText target, Editable s) {
                layoutNome.setError(null);
            }
        });
    }

    private void restaurarPreferenciasIdUsuario() {
        sharedPreferences = getSharedPreferences(AppUtil.APP_PREF, MODE_PRIVATE);
        idUsuario = sharedPreferences.getInt("idUsuario", -1);
        if (idUsuario < 0) {
            Intent iTarget = new Intent(ConfigActivity.this, LoginActivity.class);
            startActivity(iTarget);
            finish();
        }
    }

    private void salvarSharedPrefDadosUsuario() {
        sharedPreferences = getSharedPreferences(AppUtil.APP_PREF, MODE_PRIVATE);
        SharedPreferences.Editor salvarDados = sharedPreferences.edit();
        salvarDados.putString("emailUsuario", usuario.getEmail());
        salvarDados.apply();
    }

}