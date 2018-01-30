package br.com.coffani.starstore.network;

import android.os.Build;


import br.com.coffani.starstore.BuildConfig;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Coffani on 19/01/2018.
 */

public class NetworkClient {

    private  static Retrofit retrofit;
    //SOLICITAÇÃO DO CLIENT
    public static Retrofit getRetrofit() {
        if (retrofit == null){
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            //RECEPTOR QUE OUVE E MONITORA A RESPOSTA DO CLIENTE
            //OS DADOS MONITORADOS SÃO TRAZIDOS EM ORDEM
            OkHttpClient okHttpClient = builder.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)//CONSTRUINDO O MAPA DA URL ATRAVEZ DO BUILDER
                    .addConverterFactory(GsonConverterFactory.create())//CONVERTENDO DO JSON PARA JAVA
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
