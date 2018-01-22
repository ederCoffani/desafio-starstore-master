package br.com.coffani.starstore.feature.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.coffani.starstore.R;
import br.com.coffani.starstore.database.DatabaseManagerUser;
import br.com.coffani.starstore.domain.User;
import br.com.coffani.starstore.feature.home.MainActivity;
import br.com.coffani.starstore.feature.register.RegisterUserActivity;
import br.com.coffani.starstore.firebase.NetworkConfigFirebase;
import br.com.coffani.starstore.helper.Base64Custom;
//import br.com.coffani.starstore.helper.PreferenciasAndroid;

/**
 * Created by Coffani on 20/01/2018.
 */

public class LoginActivity extends AppCompatActivity {

    private EditText eEmail, ePassword;
    private Button entrar;
    private TextView registrar;
    private String email;
    private String password;
    private Cursor comprobar;
//    private PreferenciasAndroid prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        prefManager = new PreferenciasAndroid(this);
//        if (!prefManager.isFirstTimeLaunch()) {
//            launchHomeScreen();
//            finish();
//        }

//        // Making notification bar transparent
//        if (Build.VERSION.SDK_INT >= 21) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        }


        eEmail = (EditText) findViewById(R.id.email_user);
        ePassword = (EditText) findViewById(R.id.password_user);
        entrar = (Button) findViewById(R.id.button_entrar);
        registrar = (TextView) findViewById(R.id.register_user);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterUserActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciar();

            }
        });
    }

    private void iniciar() {

        if (!validar()) return;

        email = eEmail.getText().toString();
        password = ePassword.getText().toString();

        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Iniciando...");
        progressDialog.show();


        final DatabaseManagerUser databaseManager = new DatabaseManagerUser(getApplicationContext());

        eEmail.getText().clear();
        ePassword.getText().clear();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        if (databaseManager.comprobarRegistro(email)) {
                            comprobar = databaseManager.getDb().rawQuery("SELECT email, password FROM user" + " WHERE email='" + email + "' AND password='" + password + "'", null);
                            if (comprobar.moveToFirst()) {
//
//                                PreferenciasAndroid.saveSharedSetting(LoginActivity.this, "ClipCodes", "false");
//                                PreferenciasAndroid.SharedPrefesSAVE(getApplicationContext(), eEmail.getText().toString());

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("IDENT", email);
                                startActivity(intent);
                                finish();
                                progressDialog.dismiss();
                            } else {
                                eEmail.setText(email);
                                progressDialog.dismiss();
                                String mesg = String.format("Senha incorreta", null);
                                Toast.makeText(getApplicationContext(), mesg, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            progressDialog.dismiss();
                            String mesg = String.format("O e-mail que você digitou não corresponde a nenhuma conta", null);
                            Toast.makeText(getApplicationContext(), mesg, Toast.LENGTH_LONG).show();
                        }
                    }
                }, 3000);

    }

    private boolean validar() {
        boolean valid = true;

        String email = eEmail.getText().toString();
        String password = ePassword.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            eEmail.setError("Digite um endereço de e-mail válido");
            valid = false;
        } else {
            eEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            ePassword.setError("Entre 4 e 10 caracteres alfanuméricos");
            valid = false;
        } else {
            ePassword.setError(null);
        }

        return valid;
    }

//    private void launchHomeScreen() {
//        prefManager.setFirstTimeLaunch(false);
//        startActivity(new Intent(LoginActivity.this, MainActivity.class));
//        finish();
//    }


}
