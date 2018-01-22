package br.com.coffani.starstore.feature.home;

import android.util.Log;

import java.util.List;


import br.com.coffani.starstore.base.ui.BaseActivity;
import br.com.coffani.starstore.base.ui.BasePresenter;
import br.com.coffani.starstore.domain.Product;
import br.com.coffani.starstore.network.NetworkCallbak;
import rx.Observable;

/**
 * Created by Coffani on 19/01/2018.
 */

public class MainPresenter extends BasePresenter<MainView>{


    MainPresenter(MainView view){
        super.attachView(view);
    }




    void loadData(String offset){
        view.showLoading();
        addSubscribe( service.getListStore(offset), new NetworkCallbak<List<Product>>() {

            @Override
            public void onSucsess(List<Product> model) {
                view.getDataSuccess(model);
                Log.e("onSucsess", view.toString());
            }

            @Override
            public void onFailure(String message) {
                view.getDataFail(message);
            }

            @Override
            public void onFinish() {
                view.hideLoaging();
            }
        });


    }




}
