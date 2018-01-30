package br.com.coffani.starstore.base.ui;

import java.util.ArrayList;


import br.com.coffani.starstore.domain.Product;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
//CRIADO UM ASSINATURA OU RECURSO PARA COM A URL
public interface StoreService {
    @GET("products")//SERA CHAMADO ASSINCRONA DO PRODUTO UNINDO COM A URL BASE QUE SE ENCONTRA NO BUILD DA APP....
    Observable<ArrayList<Product>> getListStore(@Query("offset") String offset);
}
