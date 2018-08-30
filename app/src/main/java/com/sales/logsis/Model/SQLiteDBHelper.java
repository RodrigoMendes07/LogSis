package com.sales.logsis.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class SQLiteDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NOME="info.db";
    private static final int DATABASE_VERSAO=1;

    //estrutura da tabela para armazenar dados do usuário
    public static final String TABELA_NOME="usuario";
    public static final String COLUMN_ID = "usuarioID";
    public static final String COLUMN_NOME = "nomeUsuario";
    public static final String COLUMN_EMAIL ="emailUsuario";
    public static final String COLUMN_SENHA = "senhaUsuario";
    public static final String COLUMN_FONE = "foneUsuario";

    private static final String CRIAR_TABELA_QUERY="CREATE TABLE "+ TABELA_NOME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_NOME + " TEXT, " +
            COLUMN_EMAIL + " TEXT, " +
            COLUMN_SENHA + " TEXT, " +
            COLUMN_FONE + " TEXT " + ")";

    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NOME, null, DATABASE_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CRIAR_TABELA_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABELA_NOME);
        onCreate(db);
    }
}
