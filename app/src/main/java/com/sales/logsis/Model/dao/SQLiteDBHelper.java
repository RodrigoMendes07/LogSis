package com.sales.logsis.Model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alberto on 9/12/17.
 */

public class SQLiteDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "info.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABELA_NOME = "usuario";
    public static final String COLUMN_ID =  "idUsuario";
    public static final String COLUMN_NOME =  "nomeUsuario";
    public static final String COLUMN_EMAIL =  "emailUsuario";
    public static final String COLUMN_SENHA =  "senhaUsuario";
    public static final String COLUMN_TELEFONE =  "foneUsuario";

    private static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABELA_NOME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOME + " TEXT, "+
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_SENHA + " TEXT, " +
                    COLUMN_TELEFONE + " TEXT " + ")";

    //modified constructor
    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABELA_NOME);
        onCreate(sqLiteDatabase);
    }
}
