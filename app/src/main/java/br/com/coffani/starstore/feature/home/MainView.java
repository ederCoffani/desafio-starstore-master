package br.com.coffani.starstore.feature.home;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;

import java.util.List;

import br.com.coffani.starstore.domain.Product;


/**
 * Created by Coffani on 19/01/2018.
 */



public interface MainView {
    void showLoading();//INICIAÇÃO DAS VIEWS
    void hideLoaging();//HIDER DO PROGRESSBAR
    List<Product> getDataSuccess(List<Product> pList);//LISTE PRODUTOS MODELS
    void getDataFail(String message);//AVISOS DE ERROS
//    void initViews();// INIC

}
