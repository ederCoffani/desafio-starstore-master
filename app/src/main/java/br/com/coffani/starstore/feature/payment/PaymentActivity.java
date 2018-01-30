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

        //INTANCIANDO O OBJETO
        card = new Card();
        textView_total = (TextView) findViewById(R.id.textView_total);

        recyclerView = (RecyclerView) findViewById(R.id.cart_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);



        //ADAPTANDO OS PRODUTOS NO RECYCLER VIEW
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
            public void onClick(View v) {//CLICK NO BOTÃO DE PAGAMENTO

                addPayment();
                recyclerView.clearOnChildAttachStateChangeListeners();

            }
        });
    }

    public void addPayment() {
        if (!validar()) return;//SE A VALIDAÇÃO DER CERTO É INSERIDO OS PRODUTOS NO DB SQLITE E NO DB FB

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
        progressDialog.show();//POSTANDO A COMPRA

        card = new Card();//INSTANCIA

        card.setValue(PaymentPresenter.getInstance().getSubtotal());//VALOR
        card.setCard_holder_name(name_card.getText().toString());//NOME

        card.setCard_number(quatros_primeiro.getText().toString()//CRIAÇÃO DOS 16 NUMEROS DO CARTÃO
                + quatros_segundo.getText().toString()
                + quatros_terceiro.getText().toString()
                + quatros_quarto.getText().toString());

        card.setCvv(cvv_card.getText().toString());//CODIGO DE VERIFICAÇÃO
        card.setExp_date(date_exp_card.getText().toString());//DATA EXPEDIDORA

        registrarHistoric();// INSERINDO NO DB SQLITE
        card.salvar();//POST DO CARTÃO PARA O DB FB
        backhome();//SE TUDO DER CERTO VOLTA PARA MAIN

    }
    @Override
    public void registrarHistoric() {
        if (!validar()) return;//SE DER TUDO CERTO

        progressDialog = new ProgressDialog(PaymentActivity.this,
                R.style.AppTheme_Dark_Dialog);

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Eviando dados do cliente...");
        progressDialog.show();//MESAGE DE DIALOG


        Date dataHoraAtual = new Date();// CRIANDO A DATA E A HORA E PEGANDO DO DISPOSITIVO
        @SuppressLint("SimpleDateFormat") String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
        @SuppressLint("SimpleDateFormat") String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);

        sValue = textView_total.getText().toString();
        sDateTime = hora + " - " + data;// HORA E DATA
        sName = name_card.getText().toString();
        //4X4 = 16 NUMEROS DO CARTÃ0
        sQuatrosPrimeiro = quatros_primeiro.getText().toString();
        sQuatrosSegundos = quatros_segundo.getText().toString();
        sQuatrosTerceiros = quatros_terceiro.getText().toString();
        sQuatrosQuartos = quatros_quarto.getText().toString();

        sCvv = cvv_card.getText().toString();//CODIGO DE VERIFICAÇÃO
        sDateExp = date_exp_card.getText().toString();// DATA EXPEDIDORA

        managerTransition = new DatabaseManagerTransition(this);//CRIANDO UMA INSTANCIA DA CLASSE DE TRANSIÇÃO

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // SE FOR VALIDO ENTRA AQUI E INSERI E MOSTRA QUE FOI FEITA E O NOME O DONO DO CARTÃO
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
    public void backhome() {//INTENT PARA MAIN
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
    //METODO DE VALIDAÇÃO
    public boolean validar() {
        boolean valid = true;//VALIDO TEM QUE SER VERDADEIRO


        sName = name_card.getText().toString();

        sQuatrosPrimeiro = quatros_primeiro.getText().toString();
        sQuatrosSegundos = quatros_primeiro.getText().toString();
        sQuatrosTerceiros = quatros_primeiro.getText().toString();
        sQuatrosQuartos = quatros_primeiro.getText().toString();

        sCvv = cvv_card.getText().toString();
        sDateExp = date_exp_card.getText().toString();
        //SE O COMPRIMENTO DO TEXT FOI IGUAL A 0 OU VAZIO ENTÃO FALSO E NÃO É VALIDO
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

        }//SE NÃO SE OS 4X4 = 16 FOREM MENOR OU IGUAL A 3 OU CODIGO DE VERIFICAÇÃO FOR MENOR IGUAL A 2 É FALSO TAMBEM
        else if (quatros_primeiro.getText().length() <= 3 ||
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
        } else {//SE FOR VERDADEIRO ENTRA AQUI
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
    @Override//VOLTAR
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
    @Override// DEPOIS DE CONCLUIDO O PROGRESS É DESTRUIDO
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

}