package br.com.coffani.starstore.feature.payment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.coffani.starstore.R;
import br.com.coffani.starstore.adapter.CartAdapter;
import br.com.coffani.starstore.base.mvp.MvpActivity;
import br.com.coffani.starstore.database.DatabaseManagerTransition;
import br.com.coffani.starstore.domain.Card;
import br.com.coffani.starstore.domain.Product;
import br.com.coffani.starstore.feature.home.MainActivity;


/**
 * Created by Coffani on 20/01/2018.
 */

public class PaymentActivity extends MvpActivity<PaymentPresenter> implements PaymentView {

    private EditText name_card;
    private EditText quatros_primeiro;
    private EditText quatros_segundo;
    private EditText quatros_terceiro;
    private EditText quatros_quarto;
    private EditText cvv_card;
    private EditText date_exp_card;
    private Button button_payment;
    private TextView textView_total;
    private Toolbar toolbar;

    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;

    private CartAdapter adapter = new CartAdapter(this);

    private String sValue, sCvv, sDateExp, sDateTime, sDigits, sQuatrosPrimeiro, sQuatrosSegundos, sQuatrosTerceiros, sQuatrosQuartos, sName;

    private Card card;

    private DatabaseManagerTransition managerTransition;


    @Override
    protected PaymentPresenter createPresenter() {
        return new PaymentPresenter(this);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(R.color.colorBlanco);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Pagamentos");

        card = new Card();
        textView_total = (TextView) findViewById(R.id.textView_total);

        recyclerView = (RecyclerView) findViewById(R.id.cart_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);




        adapter.mostrasListaDeItens(PaymentPresenter.getInstance().getProducts());
        textView_total.setText(String.format("$%s", PaymentPresenter.getInstance().getSubtotal()));


        name_card = ((EditText) findViewById(R.id.name_card));

        quatros_primeiro = ((EditText) findViewById(R.id.quatros_primeiros));
        quatros_segundo = ((EditText) findViewById(R.id.quatros_segundos));
        quatros_terceiro = ((EditText) findViewById(R.id.quatros_terceiros));
        quatros_quarto = ((EditText) findViewById(R.id.quatros_quartos));

        cvv_card = ((EditText)findViewById(R.id.cvv_card));
        date_exp_card = ((EditText) findViewById(R.id.date_exp_card));

        button_payment = (Button)findViewById(R.id.button_payment);

        button_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addPayment();
                recyclerView.clearOnChildAttachStateChangeListeners();

            }
        });
    }

    public void addPayment() {
        if (!validar()) return;

        sName = name_card.getText().toString();

        sQuatrosPrimeiro = quatros_primeiro.getText().toString();
        sQuatrosSegundos = quatros_segundo.getText().toString();
        sQuatrosTerceiros = quatros_terceiro.getText().toString();
        sQuatrosQuartos = quatros_quarto.getText().toString();

        sCvv = cvv_card.getText().toString();
        sDateExp = date_exp_card.getText().toString();

        progressDialog = new ProgressDialog(this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Post do Card...");
        progressDialog.show();

        card = new Card();

        card.setValue(PaymentPresenter.getInstance().getSubtotal());
        card.setCard_holder_name(name_card.getText().toString());

        card.setCard_number(quatros_primeiro.getText().toString()
                + quatros_segundo.getText().toString()
                + quatros_terceiro.getText().toString()
                + quatros_quarto.getText().toString());

        card.setCvv(cvv_card.getText().toString());
        card.setExp_date(date_exp_card.getText().toString());

        registrarHistoric();
        card.salvar();

    }

    public void registrarHistoric() {
        if (!validar()) return;

        progressDialog = new ProgressDialog(PaymentActivity.this,
                R.style.AppTheme_Dark_Dialog);

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Eviando dados do cliente...");
        progressDialog.show();


        Date dataHoraAtual = new Date();
        @SuppressLint("SimpleDateFormat") String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
        @SuppressLint("SimpleDateFormat") String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);

        sValue = textView_total.getText().toString();
        sDateTime = hora + " - " + data;
        sName = name_card.getText().toString();

        sQuatrosPrimeiro = quatros_primeiro.getText().toString();
        sQuatrosSegundos = quatros_segundo.getText().toString();
        sQuatrosTerceiros = quatros_terceiro.getText().toString();
        sQuatrosQuartos = quatros_quarto.getText().toString();

        sCvv = cvv_card.getText().toString();
        sDateExp = date_exp_card.getText().toString();

        managerTransition = new DatabaseManagerTransition(this);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        managerTransition.insertar_parametros(null, sQuatrosQuartos, sDateTime, sValue, sName);
                        Log.e("Iserindo: ", managerTransition.getHistoricsList().toString());
                        Toast.makeText(getBaseContext(), "Transação feita por " + sName + " foi salva no BBDD", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);


                        startActivity(intent);
                        finish();
                        finishAffinity();
                        progressDialog.dismiss();
                    }
                }, 3000);

    }


    @Override
    public void backhome() {
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    private boolean validar() {
        boolean valid = true;


        sName = name_card.getText().toString();

        sQuatrosPrimeiro = quatros_primeiro.getText().toString();
        sQuatrosSegundos = quatros_primeiro.getText().toString();
        sQuatrosTerceiros = quatros_primeiro.getText().toString();
        sQuatrosQuartos = quatros_primeiro.getText().toString();

        sCvv = cvv_card.getText().toString();
        sDateExp = date_exp_card.getText().toString();

        if (name_card.getText().length() == 0 || name_card.getText().toString().equals("") &&
                quatros_primeiro.getText().length() == 0 || quatros_primeiro.getText().toString().equals("") &&
                quatros_segundo.getText().length() == 0 || quatros_segundo.getText().toString().equals("") &&
                quatros_terceiro.getText().length() == 0 || quatros_terceiro.getText().toString().equals("") &&
                quatros_quarto.getText().length() == 0 || quatros_quarto.getText().toString().equals("") &&
                cvv_card.getText().length() == 0 || cvv_card.getText().toString().equals("") &&
                date_exp_card.getText().length() == 0 || date_exp_card.getText().toString().equals("") ) {
            name_card.setError("*Name");
            quatros_primeiro.setError("*Number");
            quatros_segundo.setError("*Number");
            quatros_terceiro.setError("*Number");
            quatros_quarto.setError("*Number");
            cvv_card.setError("*Number");
            date_exp_card.setError("Exp");
            valid = false;
        }else if (quatros_primeiro.getText().length() <= 3 ||
                quatros_primeiro.getText().length() <= 3 ||
                quatros_primeiro.getText().length() <= 3 ||
                quatros_primeiro.getText().length() <= 3 ||
                cvv_card.getText().length() <=2){
            quatros_primeiro.setError("*4");
            quatros_segundo.setError("*4");
            quatros_terceiro.setError("*4");
            quatros_quarto.setError("*4");
            cvv_card.setError("*3");
            valid = false;
        } else {
            name_card.setError(null);
            quatros_primeiro.setError(null);
            quatros_segundo.setError(null);
            quatros_terceiro.setError(null);
            quatros_quarto.setError(null);
            cvv_card.setError(null);
            date_exp_card.setError(null);
        }
        return valid;
    }
    @Override
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

}