package br.com.coffani.starstore.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Maycol Meza on 15/04/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {//CLASSE DE AJUDA E MANIPULAÇÃO E CRIAÇÃO DO DB

    private static final String DB_NAME = "starstore.sqlite";//NOME DO BANCO
    private static int DB_SCHEME_VERSION = 2;//VERSÃO CRIADA

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_SCHEME_VERSION);
    }//OBTENDO A BASE DE DADOS COM O NOME DO NOVO BANCO DE DADOS

    @Override
    public void onCreate(SQLiteDatabase db) {
        // criando table
        db.execSQL(DatabaseManagerTransition.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //DROPANDO O BANCO
        db.execSQL("DROP TABLE IF EXISTS "+DB_NAME);
        onCreate(db);
    }

}

