package br.com.coffani.starstore.feature.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import br.com.coffani.starstore.feature.home.MainActivity;
import br.com.coffani.starstore.feature.payment.PaymentPresenter;
import br.com.coffani.starstore.R;
import br.com.coffani.starstore.base.mvp.MvpActivity;
import br.com.coffani.starstore.domain.Product;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends MvpActivity<DetailPresenter> implements DetailView, View.OnClickListener {
    @BindView(R.id.bt_adicionar)
    Button bt_payment;
    @BindView(R.id.bt_voltar)
    Button bt_voltar;
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
        setContentView(R.layout.activity_item_view);
        ButterKnife.bind(this);

        if (getIntent() != null && getIntent().hasExtra("ITEM")) {
            final Product product = getIntent().getParcelableExtra("ITEM");

            tValor.setText(String.valueOf(product.getValor()));
            Glide.with(DetailActivity.this).load(product.getUrlFoto()).into(tvImagemText);

            tTitulo.setText(product.getTitulo());
            tVendedor.setText(product.getLoja());

            bt_payment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PaymentPresenter.getInstance().addItem(product);
//                    String codigo;
////                    cursor.moveToPosition(position);
////                    codigo = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.ID));
////                    Intent intent = new Intent(DetailActivity.this, PaymentActivity.class);
////                    intent.putExtra("codigo", codigo);
////                    startActivity(intent);
////                    finish();

                    Toast.makeText(DetailActivity.this, "Product adicionado!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        } else {
            Toast.makeText(this, "Nada para mostrar :(", Toast.LENGTH_SHORT).show();
            finish();
        }
        bt_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
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
