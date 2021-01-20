package com.tmidev.crudsqlite.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
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

public class CriarContaActivity extends AppCompatActivity {

    TextInputLayout layoutNome, layoutEmail, layoutSenha;
    EditText editTextNome, editTextEmail, editTextSenha;
    CheckBox checkBoxTermo;
    Button buttonCriarConta;

    UsuarioModel usuarioModel;
    UsuarioController usuarioController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);

        iniciarObj();

        buttonCriarConta.setOnClickListener(v -> {
            if (verificarFormulario()) {
                buttonCriarConta.setEnabled(false);

                usuarioModel.setNome(editTextNome.getText().toString());
                usuarioModel.setEmail(editTextEmail.getText().toString());
                usuarioModel.setSenha(AppUtil.gerarMD5Hash(editTextSenha.getText().toString()));
                usuarioModel.setData(AppUtil.currentDateTime());

                usuarioController.incluirUsuario(usuarioModel);

                Intent iTarget = new Intent(CriarContaActivity.this, LoginActivity.class);
                startActivity(iTarget);
                finish();
            }
        });

    }

    private void iniciarObj() {
        layoutNome = findViewById(R.id.layoutNome);
        layoutEmail = findViewById(R.id.layoutEmail);
        layoutSenha = findViewById(R.id.layoutSenha);
        editTextNome = findViewById(R.id.editTextNome);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSenha = findViewById(R.id.editTextSenha);
        checkBoxTermo = findViewById(R.id.checkBoxTermo);
        buttonCriarConta = findViewById(R.id.buttonCriarConta);

        usuarioModel = new UsuarioModel();
        usuarioController = new UsuarioController(this);

        verificarFormularioOnFocusChange();

    }

    private boolean verificarFormulario() {
        boolean isFormularioCompleto = true;
        if (TextUtils.isEmpty(editTextSenha.getText().toString().trim())) {
            layoutSenha.setError("Informe uma senha");
            layoutSenha.requestFocus();
            isFormularioCompleto = false;
        }
        if (TextUtils.isEmpty(editTextEmail.getText().toString().trim())) {
            layoutEmail.setError("Informe seu email");
            layoutEmail.requestFocus();
            isFormularioCompleto = false;
        }
        if (TextUtils.isEmpty(editTextNome.getText().toString().trim())) {
            layoutNome.setError("Informe seu nome");
            layoutNome.requestFocus();
            isFormularioCompleto = false;
        }
        if (isFormularioCompleto) {
            if (!checkBoxTermo.isChecked()) {
                Toast.makeText(this, "É necessario aceitar os Termos.", Toast.LENGTH_SHORT).show();
                isFormularioCompleto = false;
            }
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

    public void verTermo(View view) {
        final Context context = view.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.content_termos_de_uso, null, false);
        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Termos de Uso:")
                .setPositiveButton("Aceitar", (dialog, id) -> {
                    dialog.cancel();
                })
                .setNegativeButton("Recusar", (dialog, id) -> {
                    Intent iTarget = new Intent(CriarContaActivity.this, LoginActivity.class);
                    startActivity(iTarget);
                    finish();
                }).show();
    }

    public void fazerLogin(View view) {
        Intent iFazerLogin = new Intent(CriarContaActivity.this, LoginActivity.class);
        startActivity(iFazerLogin);
    }


}