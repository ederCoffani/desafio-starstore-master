package br.com.coffani.starstore.feature.detail;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import br.com.coffani.starstore.feature.payment.PaymentPresenter;
import br.com.coffani.starstore.R;
import br.com.coffani.starstore.base.mvp.MvpActivity;
import br.com.coffani.starstore.domain.Product;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends MvpActivity<DetailPresenter> implements DetailView, View.OnClickListener {
    @BindView(R.id.fab_payment)
    FloatingActionButton fab_payment;
    @BindView(R.id.fotoImageView)
    ImageView tvImagemText;
    @BindView(R.id.tv_valor)
    TextView tValor;
    @BindView(R.id.tv_titulo)
    TextView tTitulo;
    @BindView(R.id.tv_vendedor)
    TextView tVendedor;


    @Override
    protected DetailPresenter createPresenter() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_item);
        ButterKnife.bind(this);

        if (getIntent() != null && getIntent().hasExtra("ITEM")) {
            final Product product = getIntent().getParcelableExtra("ITEM");

            tValor.setText(String.valueOf(product.getValor()));
            Glide.with(DetailActivity.this).load(product.getUrlFoto()).into(tvImagemText);
            tTitulo.setText(product.getTitulo());
            tVendedor.setText(product.getLoja());

            fab_payment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PaymentPresenter.getInstance().addItem(product);
                    finish();
                    Toast.makeText(DetailActivity.this, "Product adicionado!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Nada para mostrar :(", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getDataSucess(Product product) {

    }

    @Override
    public void getDataFail(String message) {

    }

    @Override
    public void refreshData() {

    }
}
