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
    }
}
