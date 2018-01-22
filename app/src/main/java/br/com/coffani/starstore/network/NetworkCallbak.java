package br.com.coffani.starstore.network;

import android.util.Log;

import retrofit2.HttpException;
import rx.Subscriber;

/**
 * Class que define a coneção a internet
 * Created by Coffani on 19/01/2018.
 */

public abstract class NetworkCallbak<M> extends Subscriber<M> {
    private static final String TAG = NetworkCallbak.class.getName();

    public abstract void onSucsess(M model);

    public abstract void onFailure(String message);

    public abstract void onFinish();

    @Override
    public void onCompleted() {
        onFinish();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException){
            HttpException httpException = (HttpException)e;
            int code = httpException.code();
            String message = httpException.getMessage();
            Log.i(TAG, "code :"+ code);
            onFailure(message);
        }
        onFailure(e.getMessage());
    }

    @Override
    public void onNext(M m) {
        onSucsess(m);
    }
}
