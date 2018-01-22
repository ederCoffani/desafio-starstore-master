package br.com.coffani.starstore.base.ui;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Coffani on 19/01/2018.
 */

public class BaseActivity extends AppCompatActivity{

    public Activity activity;
    CompositeSubscription compositeSubscription;
    List<Call> calls;

    @Override
    public void setContentView(@LayoutRes int layoutResID){
        super.setContentView(layoutResID);
        activity = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onCancelled();
        onUnsubscribe();
    }
    private void onCancelled(){
        if (calls != null && calls.size() > 0){
            for (Call call : calls){
                if (!call.isCanceled()){
                    call.cancel();
                }
            }
        }
    }
    private void onUnsubscribe(){
        if (compositeSubscription != null && compositeSubscription.hasSubscriptions()){
            compositeSubscription.unsubscribe();
        }
    }
}
