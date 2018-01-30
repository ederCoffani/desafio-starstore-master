package br.com.coffani.starstore.base.ui;

import android.util.Log;

import br.com.coffani.starstore.network.NetworkClient;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Coffani on 19/01/2018.
 */

public class BasePresenter<V> {
    public V view;
    protected StoreService service;
    private CompositeSubscription compositeSubscription;
    private Subscriber subscriber;

    public void attachView(V view) {
        this.view = view;
        service = NetworkClient.getRetrofit().create(StoreService.class);
    }

    public void dattachView() {
        this.view = null;
        onUnSubscribe();

    }

    public void onUnSubscribe() {
        if (compositeSubscription != null && compositeSubscription.hasSubscriptions()) {
            compositeSubscription.unsubscribe();
            Log.e("TAG", "onUnSubscribe: ");
        }
    }

    protected void addSubscribe(Observable observable, Subscriber subscriber) {
        this.subscriber = subscriber;
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
        }
        compositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber));
    }
    public void onStop(){
        if (subscriber != null){
            subscriber.unsubscribe();
        }
    }

}
