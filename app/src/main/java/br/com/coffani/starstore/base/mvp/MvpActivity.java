package br.com.coffani.starstore.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import br.com.coffani.starstore.base.ui.BaseActivity;
import br.com.coffani.starstore.base.ui.BasePresenter;


/**
 * Created by Coffani on 19/01/2018.
 */

public abstract class MvpActivity <P extends BasePresenter> extends BaseActivity {

    protected P presenter;

    protected abstract P createPresenter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = createPresenter();
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (presenter != null){
            presenter.dattachView();
        }
    }
}
