package br.com.coffani.starstore.feature.detail;

import java.io.DataInput;

import br.com.coffani.starstore.base.ui.BasePresenter;
import br.com.coffani.starstore.domain.Product;
import br.com.coffani.starstore.network.NetworkCallbak;

/**
 * Created by Coffani on 19/01/2018.
 */

public class DetailPresenter extends BasePresenter<DetailView> {
    DetailPresenter(DetailView view){
        super.attachView(view);
    }

    void loadData(String offsat){
        view.showLoading();
//        addSubscribe(service.getListStore(offsat), new NetworkCallbak<Product>() {
//
//
//            @Override
//            public void onSucsess(final Product model) {
//                view.getDataSucess(model);
//            }
//
//            @Override
//            public void onFailure(String message) {
//                view.getDataFail(message);
//            }
//
//            @Override
//            public void onFinish() {
//                view.hideLoading();
//            }
//        });
    }
}
