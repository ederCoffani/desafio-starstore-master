package br.com.coffani.starstore.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;



import java.util.ArrayList;
import java.util.List;

import br.com.coffani.starstore.domain.User;

/**
 * Created by Maycol Meza on 15/04/2017.
 */

public class DatabaseManagerUser extends DatabaseManager {

    private static final String NAME_TABLA = "user";
    private static final String CN_ID = "_id";
    private static final String CN_EMAIL = "email";
    private static final String CN_PASSWORD = "password";
    private static final String CN_IMAGE = "imagen";
    private static final String CN_NAME = "name";

    public static final String CREATE_TABLE = "create table " + NAME_TABLA + " ("
            + CN_ID + " integer PRIMARY KEY AUTOINCREMENT, "
            + CN_EMAIL + " text NULL, "
            + CN_PASSWORD + " text NULL, "
            + CN_IMAGE + " BLOB NULL, "
            + CN_NAME + " text NOT NULL "
            + ");";

    public DatabaseManagerUser(Context ctx) {
        super(ctx);
    }

    @Override
    public void exit() {
        super.getDb().close();
    }

    private ContentValues generarContentValues(String id, String email, String password, byte[] imagen, String name){
        ContentValues valores = new ContentValues();
        valores.put(CN_ID, id);
        valores.put(CN_EMAIL, email);
        valores.put(CN_PASSWORD, password);
        valores.put(CN_IMAGE, imagen);
        valores.put(CN_NAME, name);

        return valores;
    }


    public void insertar_parametros(String id, String email, String password, byte[] imagen, String name) {
        Log.d("usuario_insertar", super.getDb().insert(NAME_TABLA,null,generarContentValues(id, email, password, imagen, name))+"");
    }

    public void actualizar_parametros(String id, String email, String pass, byte[] imagen, String name) {

        ContentValues valores = new ContentValues();
        valores.put(CN_ID, id);
        valores.put(CN_EMAIL, email);
        valores.put(CN_PASSWORD, pass);
        valores.put(CN_IMAGE, imagen);
        valores.put(CN_NAME, name);

        String [] args = new String[]{id};

        Log.d("actualizar", super.getDb().update(NAME_TABLA, valores,"_ID=?", args)+"");
    }

    @Override
    public void eliminar(String id) {

        super.getDb().delete(NAME_TABLA, CN_ID +"=?", new String[]{id});
    }

    @Override
    public void eliminarTodo() {

        super.getDb().execSQL("DELETE FROM "+ NAME_TABLA+";");
    }

    @Override
    public Cursor cargarCursor() {

        String [] columnas = new String[]{CN_ID, CN_EMAIL, CN_PASSWORD, CN_IMAGE, CN_NAME};

        return super.getDb().query(NAME_TABLA, columnas, null, null, null, null, null);
    }

    @Override
    public boolean comprobarRegistro(String email) {

        boolean esta;
        Cursor resultSet = super.getDb().rawQuery("SELECT email FROM user" + " WHERE email='"+email+"'", null);

        if(resultSet.getCount()<=0){
            esta = false;
        }else{
            esta = true;
        }
        return esta;
    }

    public List<User> getUsuariosList(){

        List<User> list = new ArrayList<>();
        Cursor c = cargarCursor();

        while (c.moveToNext()){
            User usuario = new User();

            usuario.setId(c.getString(0));
            usuario.setEmail(c.getString(1));
            usuario.setPassword(c.getString(2));
            usuario.setBytes(c.getBlob(3));
            usuario.setName(c.getString(4));
            //usuario.setActive(false);

            list.add(usuario);
        }

        return list;
    }

    public User getUsuario(String ident){

        Cursor c1 = super.getDb().rawQuery("SELECT _id, email, password, imagen, name FROM user WHERE email" + "='" + ident+ "'", null);

        User user = new User();

        c1.moveToNext();

        user.setId(c1.getString(0));
        user.setEmail(c1.getString(1));
        user.setPassword(c1.getString(2));
        user.setBytes(c1.getBlob(3));
        user.setName(c1.getString(4));
        return user;
    }
}
