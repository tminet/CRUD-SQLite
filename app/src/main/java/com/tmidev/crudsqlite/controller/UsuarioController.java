package com.tmidev.crudsqlite.controller;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.Nullable;

import com.tmidev.crudsqlite.database.AppSQLite;
import com.tmidev.crudsqlite.datamodel.UsuarioDataModel;
import com.tmidev.crudsqlite.model.UsuarioModel;

import java.util.List;

public class UsuarioController extends AppSQLite {

    private static final String TABELA = UsuarioDataModel.TABELA;
    private ContentValues dados;

    public UsuarioController(@Nullable Context context) {
        super(context);
    }

    public boolean incluirUsuario(UsuarioModel usuario) {
        dados = new ContentValues();
        dados.put(UsuarioDataModel.NOME, usuario.getNome());
        dados.put(UsuarioDataModel.EMAIL, usuario.getEmail());
        dados.put(UsuarioDataModel.SENHA, usuario.getSenha());
        dados.put(UsuarioDataModel.DATA, usuario.getData());
        return insert(TABELA, dados);
    }

    public UsuarioModel autenticarUsuario(UsuarioModel usuario) {
        dados = new ContentValues();
        dados.put(UsuarioDataModel.EMAIL, usuario.getEmail());
        dados.put(UsuarioDataModel.SENHA, usuario.getSenha());
        return login(TABELA, dados);
    }

    public UsuarioModel dadosUsuario(int id) {
        return retrieveUserData(UsuarioDataModel.TABELA, id);
    }

    public boolean alterarDadosUsuario(UsuarioModel usuario){
        dados = new ContentValues();
        dados.put(UsuarioDataModel.ID, usuario.getId());
        dados.put(UsuarioDataModel.NOME, usuario.getNome());
        dados.put(UsuarioDataModel.EMAIL, usuario.getEmail());
        dados.put(UsuarioDataModel.SENHA, usuario.getSenha());
        return update(TABELA, dados);
    }

    public boolean excluirUsuario(UsuarioModel usuario){
        return delete(TABELA, usuario.getId());
    }

    public List<UsuarioModel> listarUsuarios() {
        return list(TABELA);
    }

}