package br.com.coffani.starstore.network;

import java.util.ArrayList;


import br.com.coffani.starstore.domain.Product;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by coffani on 21/09/2017.
 */
//@Query("limit") int limit,
public interface StoreService {
    @GET("products")
    Observable<ArrayList<Product>> getListStore(@Query("offset") String offset);
}
