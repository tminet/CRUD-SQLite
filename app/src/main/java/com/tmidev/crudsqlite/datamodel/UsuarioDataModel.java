package com.tmidev.crudsqlite.datamodel;

public class UsuarioDataModel {

    public static final String TABELA = "usuarios";
    public static final String ID = "id";
    public static final String NOME = "nome";
    public static final String EMAIL = "email";
    public static final String SENHA = "senha";
    public static final String DATA = "data";

    public static String gerarTabela(){
        String query = "CREATE TABLE " + TABELA + " (";
        query += ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        query += NOME + " TEXT, ";
        query += EMAIL + " TEXT, ";
        query += SENHA + " TEXT, ";
        query += DATA + " TEXT";
        query += ")";
        return query;
    }

}