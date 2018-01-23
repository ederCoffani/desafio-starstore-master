package br.com.coffani.starstore.feature.payment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.coffani.starstore.R;
import br.com.coffani.starstore.adapter.CartAdapter;
import br.com.coffani.starstore.base.mvp.MvpActivity;
import br.com.coffani.starstore.database.DatabaseManagerTransition;
import br.com.coffani.starstore.domain.Card;
import br.com.coffani.starstore.feature.home.MainActivity;

import static br.com.coffani.starstore.R.style.MyAlertDialog;

/**
 * Created by Coffani on 20/01/2018.
 */

public class PaymentActivity extends MvpActivity<PaymentPresenter> implements PaymentView {

    private EditText name;
    private EditText card_number;
    private EditText cvv;
    private EditText expDate;
    private Button pedir;
    private TextView subtotal;

    private CartAdapter adapter = new CartAdapter(this);

    private String sValor, sDatahora, sDigitos, sName;

    private Card card;

    private DatabaseManagerTransition managerTransition;


    @Override
    protected PaymentPresenter createPresenter() {
        return new PaymentPresenter(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        card = new Card();
        subtotal = (TextView) findViewById(R.id.carrinho_textView);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.carrinho_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.mostrasListaDeItens(PaymentPresenter.getInstance().getProducts());
        subtotal.setText(String.format("$%s", PaymentPresenter.getInstance().getSubtotal()));



        addPayment();

    }

    public void addPayment() {
        pedir = (Button) findViewById(R.id.carrinho_button);
        pedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this, MyAlertDialog);

                final View v = LayoutInflater.from(PaymentActivity.this).inflate(R.layout.dialog_card, new FrameLayout(getBaseContext()), false);


                name = ((EditText) v.findViewById(R.id.dialog_nome));
                card_number = ((EditText) v.findViewById(R.id.dialog_cartao));
                cvv = ((EditText) v.findViewById(R.id.dialog_cvv));
                expDate = ((EditText) v.findViewById(R.id.dialog_data));


                builder.setView(v);
                builder.setTitle("Digite os dados do Cartão");
                builder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {

                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (card != null) {
                            card = new Card();



                            card.setValue(PaymentPresenter.getInstance().getSubtotal());
                            card.setCard_holder_name(name.getText().toString());
                            card.setCard_number(card_number.getText().toString());
                            card.setCvv(cvv.getText().toString());
                            card.setExp_date(expDate.getText().toString());

                            registrarHistoric();

                            card.salvar();


                            Log.e("TAG", card.toString());
                        } else {

                        }


                    }
                }).setNegativeButton("Cancelar", null);
                builder.show();
            }
        });

    }
    public void registrarHistoric() {
        final ProgressDialog progressDialog = new ProgressDialog(PaymentActivity.this,
                R.style.AppTheme_Dark_Dialog);

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Eviando dados do cliente...");
        progressDialog.show();

        Date dataHoraAtual = new Date();
        @SuppressLint("SimpleDateFormat") String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
        @SuppressLint("SimpleDateFormat") String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);

        sValor = subtotal.getText().toString();
        sDatahora = hora+" - "+data;

        String digitos = card_number.getText().toString();
        sDigitos = digitos.substring(digitos.length() - 4);

        sName = name.getText().toString();

        managerTransition = new DatabaseManagerTransition(this);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        managerTransition.insertar_parametros(null, sDigitos, sDatahora, sValor, sName);
                        Log.e("Iserindo: ", managerTransition.getHistoricsList().toString());
                        Toast.makeText(getBaseContext(), "Transação feita por "+ sName+" foi salva no BBDD", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);


                        startActivity(intent);
                        finish();
                        progressDialog.dismiss();
                    }
                }, 3000);

    }

    @Override
    public void backhome() {
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }
}