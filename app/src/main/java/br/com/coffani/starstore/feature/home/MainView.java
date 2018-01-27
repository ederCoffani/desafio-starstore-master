package br.com.coffani.starstore.feature.home;

import android.support.v7.widget.SearchView;

import java.util.List;

import br.com.coffani.starstore.domain.Product;


/**
 * Created by Coffani on 19/01/2018.
 */

public interface MainView {
    void showLoading();
    void hideLoaging();
    List<Product> getDataSuccess(List<Product> pList);
    void getDataFail(String message);
    void initViews();

}
