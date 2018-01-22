package br.com.coffani.starstore.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.coffani.starstore.domain.Historic;
import br.com.coffani.starstore.domain.User;

/**
 * Created by Coffani on 22/01/2018.
 */

public class DatabaseManagerTransition  extends DatabaseManager {
    private static final String TABELA = "historic";
    private static final String ID = "_id";
    private static final String VALOR = "valor";
    private static final String DATAHORA = "datahora";
    private static final String DIGITO = "digito";
    private static final String NAME = "name";

    private static final int VERSAO = 1;

    String sql = "CREATE TABLE"+TABELA+"("
            + ID + " integer primary key autoincrement,"
            + VALOR + " text,"
            + DATAHORA + " text,"
            + DIGITO + " text,"
            + NAME + "text"
            +")";

    public DatabaseManagerTransition(Context ctx) {
        super(ctx);
    }


    @Override
    public void exit() {
        super.getDb().close();
    }

    private ContentValues generarContentValues(String id, String valor, String datahora, String digito, String name){
        ContentValues valores = new ContentValues();
        valores.put(ID, id);
        valores.put(VALOR, valor);
        valores.put(DATAHORA, datahora);
        valores.put(DIGITO, digito);
        valores.put(NAME, name);

        return valores;
    }


    public void insertar_parametros(String id, String valor, String datahora, String digito, String name) {
        Log.d("transition_insertar", super.getDb().insert(TABELA,null,generarContentValues(id, valor, datahora, digito,name))+"");
    }

    public void actualizar_parametros(String id, String valor, String datahora, String digito, String name) {

        ContentValues valores = new ContentValues();
        valores.put(ID, id);
        valores.put(VALOR, valor);
        valores.put(DATAHORA, datahora);
        valores.put(DIGITO, digito);
        valores.put(NAME, name);

        String [] args = new String[]{id};

        Log.d("actualizar", super.getDb().update(TABELA, valores,"_ID=?", args)+"");
    }

    @Override
    public void eliminar(String id) {

        super.getDb().delete(TABELA, ID +"=?", new String[]{id});
    }

    @Override
    public void eliminarTodo() {

        super.getDb().execSQL("DELETE FROM "+ TABELA+";");
    }

    @Override
    public Cursor cargarCursor() {
        String [] columnas = new String[]{ID, VALOR, DATAHORA, DIGITO, NAME};

        return super.getDb().query(TABELA, columnas, null, null, null, null, null);
    }

    @Override
    public boolean comprobarRegistro(String name) {

        boolean esta;
        Cursor resultSet = super.getDb().rawQuery("SELECT name FROM name" + " WHERE name='"+name+"'", null);

        if(resultSet.getCount()<=0){
            esta = false;
        }else{
            esta = true;
        }
        return esta;
    }

    public List<Historic> getHistoricsList(){

        List<Historic> list = new ArrayList<>();
        Cursor c = cargarCursor();

        while (c.moveToNext()){
            Historic historic = new Historic();

            historic.setID(c.getString(0));
            historic.setData(c.getString(1));
            historic.setValue(Double.parseDouble(c.getString(2)));
            historic.setCard_holder_name(c.getString(3));
            //usuario.setActive(false);

            list.add(historic);
        }

        return list;
    }

    public Historic getHistoric(String ident){

        Cursor c1 = super.getDb().rawQuery("SELECT id, datahora, digitocart, name FROM user WHERE email" + "='" + ident+ "'", null);
        Historic historic = new Historic();

        c1.moveToNext();

        historic.setID(c1.getString(0));
        historic.setData(c1.getString(1));
        historic.setValue(Double.parseDouble(c1.getString(2)));
        historic.setCard_holder_name(c1.getString(3));
        return historic;
    }}