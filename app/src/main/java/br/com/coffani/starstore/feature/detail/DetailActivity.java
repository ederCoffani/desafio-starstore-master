package br.com.coffani.starstore.feature.detail;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;

import br.com.coffani.starstore.feature.home.MainActivity;
import br.com.coffani.starstore.feature.payment.PaymentActivity;
import br.com.coffani.starstore.feature.payment.PaymentPresenter;
import br.com.coffani.starstore.R;
import br.com.coffani.starstore.base.mvp.MvpActivity;
import br.com.coffani.starstore.domain.Product;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends MvpActivity<DetailPresenter> implements DetailView, View.OnClickListener {
    @BindView(R.id.bt_adicionar)
    Button bt_payment;
    @BindView(R.id.fotoImageView)
    ImageView tvImagemText;
    @BindView(R.id.tv_valor)
    TextView tValor;
    @BindView(R.id.tv_titulo)
    TextView tTitulo;
    @BindView(R.id.tv_vendedor)
    TextView tVendedor;
    @BindView(R.id.tb_detail)
    Toolbar tb_detail;

    Product product;

    @Override
    protected DetailPresenter createPresenter() {
        return null;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view_new);
        ButterKnife.bind(this);

        // TOOLBAR
        tb_detail.setTitle("Detalhes do Produto");
        setSupportActionBar(tb_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        product = getIntent().getParcelableExtra("ITEM");

        tValor.setText(String.valueOf(product.getPrice()));
        Glide.with(DetailActivity.this).load(product.getThumbnailHd()).into(tvImagemText);

        tTitulo.setText(product.getTitle());
        tVendedor.setText(product.getSeller());
        //CLIC PARA ADIONAR AO CARRINHO


        bt_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceder();
            }
        });

//        //TRANFERIDO OS DADOS OBTIDOS POR DETALES VINDOS DA INTENT DA CLASS STOREADAPTER
//        if (getIntent() != null && getIntent().hasExtra("ITEM")) {//REFERENCIA ITEM
//            final Product product = getIntent().getParcelableExtra("ITEM");
//
//
//            tValor.setText(String.valueOf(product.getPrice()));
//            Glide.with(DetailActivity.this).load(product.getThumbnailHd()).into(tvImagemText);
//
//            tTitulo.setText(product.getTitle());
//            tVendedor.setText(product.getSeller());
//            //CLIC PARA ADIONAR AO CARRINHO
//
//            PaymentPresenter.getInstance().addItem(product);
////            Toast.makeText(DetailActivity.this, "Produto adicionado ao carrinho, indo para o carrinho!", Toast.LENGTH_SHORT).show();
////            Intent it = new Intent(DetailActivity.this, PaymentActivity.class);
////            startActivity(it);
//            finish();
//
//        } else {// SE A INTENT NÃO ACONTECER POSSA A SER QUE A REFERENCIA ESTEJA ERRADA ENTÃO NADA ACONTECE
//            Toast.makeText(DetailActivity.this, "Nada para mostrar :(", Toast.LENGTH_SHORT).show();
//            finish();
//        }
//        finish();
    }

    @Override
    public void onClick(View view) {


    }
    public void proceder(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // Setting Alert Dialog Title
        alertDialogBuilder.setTitle("Confime e prossiga..!!!");
        // Icon Of Alert Dialog
        alertDialogBuilder.setIcon(R.drawable.ic_view_cart);
        // Setting Alert Dialog Message
        alertDialogBuilder.setMessage("Adicionar e ir para o carrinho(Carrinho)?" +
                ", " +
                "Adicionar e ver todos os produtos(Produtos)?" +
                ", " +
                "Continuar em detalhes de produtos(Detalhes)");
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Carrinho", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //TRANFERIDO OS DADOS OBTIDOS POR DETALES VINDOS DA INTENT DA CLASS STOREADAPTER
                if (getIntent() != null && getIntent().hasExtra("ITEM")) {//REFERENCIA ITEM
                    product = getIntent().getParcelableExtra("ITEM");

//                    tValor.setText(String.valueOf(product.getPrice()));
//                    Glide.with(DetailActivity.this).load(product.getThumbnailHd()).into(tvImagemText);
//
//                    tTitulo.setText(product.getTitle());
//                    tVendedor.setText(product.getSeller());
//                    //CLIC PARA ADIONAR AO CARRINHO

                    PaymentPresenter.getInstance().addItem(product);
                    Toast.makeText(DetailActivity.this, "Produto adicionado ao carrinho, indo para o carrinho!", Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(DetailActivity.this, PaymentActivity.class);
                    startActivity(it);
                    finish();

                } else {// SE A INTENT NÃO ACONTECER POSSA A SER QUE A REFERENCIA ESTEJA ERRADA ENTÃO NADA ACONTECE
                    Toast.makeText(DetailActivity.this, "Nada para mostrar :(", Toast.LENGTH_SHORT).show();
                    finish();
                }
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton("Produtos", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    //TRANFERIDO OS DADOS OBTIDOS POR DETALES VINDOS DA INTENT DA CLASS STOREADAPTER
                    if (getIntent() != null && getIntent().hasExtra("ITEM")) {//REFERENCIA ITEM
                        product = getIntent().getParcelableExtra("ITEM");

//                    tValor.setText(String.valueOf(product.getPrice()));
//                    Glide.with(DetailActivity.this).load(product.getThumbnailHd()).into(tvImagemText);
//
//                    tTitulo.setText(product.getTitle());
//                    tVendedor.setText(product.getSeller());
//                    //CLIC PARA ADIONAR AO CARRINHO

                        PaymentPresenter.getInstance().addItem(product);
                        Toast.makeText(DetailActivity.this, "Produto adicionado ao carrinho, voltando para os produtos!", Toast.LENGTH_SHORT).show();
                        Intent it = new Intent(DetailActivity.this, MainActivity.class);
                        startActivity(it);
                        finish();

                    } else {// SE A INTENT NÃO ACONTECER POSSA A SER QUE A REFERENCIA ESTEJA ERRADA ENTÃO NADA ACONTECE
                        Toast.makeText(DetailActivity.this, "Nada para mostrar :(", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    finish();
            }
        });
        alertDialogBuilder.setNeutralButton("Detalhes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"Continuando vendo o prouto",Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    @Override
    public void showLoading() {
    //PODERIA COLOCAR UM PROGRESSBAR COM ALGUMA MENSAGEM
    }
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, MainActivity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;


        }
        return true;
    }

}
