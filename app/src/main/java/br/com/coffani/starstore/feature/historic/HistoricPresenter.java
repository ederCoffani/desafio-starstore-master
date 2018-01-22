package br.com.coffani.starstore.feature.historic;

import android.content.Context;

import java.util.List;

import br.com.coffani.starstore.domain.Historic;

/**
 * Created by Coffani on 22/01/2018.
 */

public class HistoricPresenter {
    public HistoricPresenter(Context context){
        //INTEGRAÇÃO COM BANCO DE DADOS
    }

//    public boolean create(Historic historic){
//
//    }

    public int totalHistorico(){
        return 0;
    }
//    public List<Historic> listHistoric(){
//
//    }

    public  Historic buscarHistoricID(int historicID){
        return null;
    }
    public boolean update(Historic historic){
        return true;
    }
    public  boolean deletr(int historicID){
        return true;
    }
}
