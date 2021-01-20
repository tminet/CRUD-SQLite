package com.tmidev.crudsqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.tmidev.crudsqlite.datamodel.UsuarioDataModel;
import com.tmidev.crudsqlite.model.UsuarioModel;
import com.tmidev.crudsqlite.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

public class AppSQLite extends SQLiteOpenHelper {

    private final static String DB_NAME = "App_CRUDSQLite.sqlite";
    private final static int DB_VER = 1;

    SQLiteDatabase db;
    Cursor cursor;

    public AppSQLite(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(UsuarioDataModel.gerarTabela());
            Log.i(AppUtil.APP_LOG, "Tabela usuarios: " + UsuarioDataModel.gerarTabela());
        } catch (SQLException e) {
            Log.e(AppUtil.APP_LOG, "Tabela usuario erro: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * salva dados no sqlite
     */
    public boolean insert(String tabela, ContentValues dados) {
        boolean sucesso = true;
        try {
            sucesso = db.insert(tabela, null, dados) > 0;
        } catch (SQLException e) {
            Log.e(AppUtil.APP_LOG, tabela + "falha ao executar o insert(): " + e.getMessage());
            e.printStackTrace();
        }
        return sucesso;
    }

    /**
     * verifica dados para autenticação do usuario no sqlite
     */
    public UsuarioModel login(String tabela, ContentValues dados) {
        UsuarioModel usuario = new UsuarioModel();
        String email = dados.getAsString("email");
        String senha = dados.getAsString("senha");
        String sql = "SELECT * FROM " + tabela + " WHERE email = '" + email + "' AND senha = '" + senha + "'";
        try {
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                usuario.setId(cursor.getInt(cursor.getColumnIndex(UsuarioDataModel.ID)));
                usuario.setNome(cursor.getString(cursor.getColumnIndex(UsuarioDataModel.NOME)));
                usuario.setEmail(cursor.getString(cursor.getColumnIndex(UsuarioDataModel.EMAIL)));
            }
        } catch (SQLException e) {
            Log.e(AppUtil.APP_LOG, tabela + "falha ao executar o login(): " + e.getMessage());
            e.printStackTrace();
        }
        return usuario;
    }

    /**
     * retorna dados do usuario logado pelo id
     */
    public UsuarioModel retrieveUserData(String tabela, int id) {
        UsuarioModel usuario = new UsuarioModel();
        String sql = "SELECT * FROM " + tabela + " WHERE id = '" + id + "'";
        try {
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                usuario.setId(cursor.getInt(cursor.getColumnIndex(UsuarioDataModel.ID)));
                usuario.setNome(cursor.getString(cursor.getColumnIndex(UsuarioDataModel.NOME)));
                usuario.setEmail(cursor.getString(cursor.getColumnIndex(UsuarioDataModel.EMAIL)));
                usuario.setData(cursor.getString(cursor.getColumnIndex(UsuarioDataModel.DATA)));
            }
        } catch (SQLException e) {
            Log.e(AppUtil.APP_LOG, tabela + "falha ao executar o retrieveUserData(): " + e.getMessage());
            e.printStackTrace();
        }
        return usuario;
    }

    /**
     * altera dados do usuario
     */
    public boolean update(String tabela, ContentValues dados) {
        boolean sucesso = true;
        try {
            int id = dados.getAsInteger("id");
            sucesso = db.update(tabela, dados, "id=?", new String[]{Integer.toString(id)}) > 0;
        } catch (SQLException e) {
            Log.e(AppUtil.APP_LOG, tabela + "falha ao executar o update(): " + e.getMessage());
            e.printStackTrace();
        }
        return sucesso;
    }

    /**
     * deleta o usuario
     */
    public boolean delete(String tabela, int id) {
        boolean sucesso = true;
        try {
            sucesso = db.delete(tabela, "id=?", new String[]{Integer.toString(id)}) > 0;
        } catch (SQLException e) {
            Log.e(AppUtil.APP_LOG, tabela + "falha ao executar o delete(): " + e.getMessage());
            e.printStackTrace();
        }
        return sucesso;
    }

    public List<UsuarioModel> list(String tabela) {
        List<UsuarioModel> list = new ArrayList<>();
        UsuarioModel usuario;
        String sql = "SELECT * FROM " + tabela;
        try {
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                do {
                    usuario = new UsuarioModel();
                    usuario.setId(cursor.getInt(cursor.getColumnIndex(UsuarioDataModel.ID)));
                    usuario.setNome(cursor.getString(cursor.getColumnIndex(UsuarioDataModel.NOME)));
                    usuario.setEmail(cursor.getString(cursor.getColumnIndex(UsuarioDataModel.EMAIL)));
                    usuario.setData(cursor.getString(cursor.getColumnIndex(UsuarioDataModel.DATA)));
                    list.add(usuario);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e(AppUtil.APP_LOG, tabela + "falha ao executar o list(): " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

}