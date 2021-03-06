package br.com.coffani.starstore.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.coffani.starstore.domain.Historic;

/**
 * Created by Coffani on 22/01/2018.
 */

public class DatabaseManagerTransition  extends DatabaseManager {
    private static final String TABELA = "historic";//TABELA
    //ATRIBUTOS DA TABELA
    private static final String ID = "_id";
    private static final String VALOR = "valor";
    private static final String DATAHORA = "datahora";
    private static final String DIGITO = "digito";
    private static final String NAME = "name";
    //CRIANDO A TABELA
    public static final String CREATE_TABLE = "create table " + TABELA + "("
            + ID + " integer PRIMARY KEY AUTOINCREMENT,"
            + VALOR + " text NULL, "
            + DATAHORA + " text NULL, "
            + DIGITO + " text NULL, "
            + NAME + " text NULL "
            +");";

    //CONTEXTO DA CLASS
    public DatabaseManagerTransition(Context ctx) {
        super(ctx);
    }
    @Override//DEPOIS DE TUDO FEITO E CERTO FECHAR
    public void exit() {
        super.getDb().close();
    }
    //GERANDO O CONTEUDO PARA SER INSERIDO
    private ContentValues generarContentValues(String id, String valor, String datahora, String digito, String name){
        ContentValues valores = new ContentValues();
        valores.put(ID, id);
        valores.put(VALOR, valor);
        valores.put(DATAHORA, datahora);
        valores.put(DIGITO, digito);
        valores.put(NAME, name);

        return valores;
    }

    //INSERINDO OS DADOS NA TABELA
    public void insertar_parametros(String id, String valor, String datahora, String digito, String name) {
        Log.d("transition_insertar", super.getDb().insert(TABELA,null,generarContentValues(id, valor, datahora, digito, name))+"");
    }
    //ATUALIZAR DADOS
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
    //EXCLUIR DADOS
    @Override
    public void eliminar(String id) {

        super.getDb().delete(TABELA, ID +"=?", new String[]{id});
    }
    //EXCLUIR TODOS OS DADOS
    @Override
    public void eliminarTodo() {

        super.getDb().execSQL("DELETE FROM "+ TABELA+";");
    }
    //CARREGANDO OS DADOS
    @Override
    public Cursor cargarCursor() {
        String [] columnas = new String[]{ID, VALOR, DATAHORA, DIGITO, NAME};

        return super.getDb().query(TABELA, columnas, null, null, null, null, null);
    }

//    @Override
//    public boolean comprobarRegistro(String name) {
//
//        boolean esta;
//        Cursor resultSet = super.getDb().rawQuery("SELECT name FROM name" + " WHERE name='"+name+"'", null);
//
//        if(resultSet.getCount()<=0){
//            esta = false;
//        }else{
//            esta = true;
//        }
//        return esta;
//    }

    //LISTAGEM DO HISTORICO DE DADOS
    public List<Historic> getHistoricsList(){

        List<Historic> list = new ArrayList<>();
        Cursor c = cargarCursor();

        while (c.moveToNext()){
            Historic historic = new Historic();

            historic.setId(c.getString(0));
            historic.setDigito(c.getString(1));
            historic.setDatahora(c.getString(2));
            historic.setValor(c.getString(3));
            historic.setName(c.getString(4));
            //usuario.setActive(false);

            list.add(historic);
        }

        return list;
    }
    //LISTAGEM POR ID
    public Historic getHistoric(String ident){

        Cursor c1 = super.getDb().rawQuery("SELECT _id, valor, datahora, digito, name FROM historic WHERE name" + "='" + ident+ "'", null);
        Historic historic = new Historic();

        c1.moveToNext();

        historic.setId(c1.getString(0));
        historic.setDatahora(c1.getString(1));
        historic.setDigito(c1.getString(2));
        historic.setValor(c1.getString(3));
        historic.setName(c1.getString(4));
        return historic;
    }}