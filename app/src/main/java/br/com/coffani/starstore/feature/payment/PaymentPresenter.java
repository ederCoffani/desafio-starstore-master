package br.com.coffani.starstore.feature.payment;

import android.app.ProgressDialog;
import android.view.View;

import java.util.ArrayList;

import br.com.coffani.starstore.R;
import br.com.coffani.starstore.base.ui.BasePresenter;
import br.com.coffani.starstore.domain.Card;
import br.com.coffani.starstore.domain.Product;

/**
 * Created by Coffai on 23/09/2017.
 */

public class PaymentPresenter extends BasePresenter<PaymentView> {

    private static PaymentPresenter instance;
    private ArrayList<Product> products = new ArrayList<>();


    PaymentPresenter(PaymentView view) {
        super.attachView(view);
    }

    private PaymentPresenter() {
    }

    public static PaymentPresenter getInstance() {
        if (instance == null) {
            instance = new PaymentPresenter();
        }
        return instance;
    }

    public void addItem(Product product) {
        products.add(product);
    }

    public double getSubtotal() {
        double total = 0;
        for (Product i : products)
            total += i.getPrice();
        return total;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
}
