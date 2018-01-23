package br.com.coffani.starstore.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Maycol Meza on 15/04/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "starstore.sqlite";
    private static int DB_SCHEME_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_SCHEME_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // crea tabla usuario
        db.execSQL(DatabaseManagerUser.CREATE_TABLE);
        db.execSQL(DatabaseManagerTransition.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+DB_NAME);
        onCreate(db);
    }

}

