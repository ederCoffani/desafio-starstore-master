package br.com.coffani.starstore.feature.home;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;
import br.com.coffani.starstore.base.ui.BasePresenter;
import br.com.coffani.starstore.domain.Product;
import br.com.coffani.starstore.network.NetworkCallbak;
/**
 * Created by Coffani on 19/01/2018.
 * PRESENTE DA MAIN ACTIVITY QUE FAZ TODA A LOGICA PARA PEGAR OS PRODUTOS
 */

public class MainPresenter extends BasePresenter<MainView>{


    //PRESENTE CAMADA LOGICA SE COMUNICANDO COM A VIEW QUE É A CAMADA QUE DE VISUALIZAÇÃO
    MainPresenter(MainView view){
        super.attachView(view);
    }

    void loadData(String offset){
        //CHAMADA E CRIACÃO DA LISTA DO PRODUTO
        view.showLoading();
        addSubscribe( service.getListStore(offset), new NetworkCallbak<List<Product>>() {

            @Override//IMPLANTANDO NA VIEW A LISTA DE PRODUTOS
            public void onSucsess(List<Product> model) {
                view.getDataSuccess(model);
                Log.e("onSucsess", view.toString());//TAG DE LOCALIZAÇÃO
            }

            @Override//SE FALHAR O PRESENTER ATRIBUI UMA MENTSAGEM A VIEW
            public void onFailure(String message) {
                view.getDataFail(message);
            }

            @Override// ATÉ A CHAMADA DA LISTA O PROGRESBAR É ATIVADO  E DEPOIS DE GATILHADA ELE PARA
            public void onFinish() {
                view.hideLoaging();
            }

        });
    }

}
