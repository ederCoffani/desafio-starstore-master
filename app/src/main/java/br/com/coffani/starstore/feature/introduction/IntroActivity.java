package br.com.coffani.starstore.feature.introduction;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.view.View;



import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;
import br.com.coffani.starstore.R;
import br.com.coffani.starstore.feature.home.MainActivity;
import br.com.coffani.starstore.feature.login.LoginActivity;
//import br.com.coffani.starstore.helper.PreferenciasAndroid;

/**
 * Created by Coffani on 21/01/2018.
 */

public class IntroActivity extends MaterialIntroActivity {
    private SharedPreferences prefManager;

    private String[] arrayPer = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableLastSlideAlphaExitTransition(true);


        //PERSISÊNCIA DE DADOS
        prefManager =  this.getSharedPreferences(
                "br.com.coffani.starstore", Context.MODE_PRIVATE);

        boolean isFirstTimeLaunch = prefManager.getBoolean("isFirstTimeLaunch", true);

        if (!isFirstTimeLaunch) {
            onFinish();
        }

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        getBackButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });


        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorSlider1)
                .buttonsColor(R.color.colorButton1)
                .image(R.mipmap.slide1)
                .title("Star Store")
                .description("Aplicativo e-commerce que tem como base produtos da marca Star Wors, inpirados no cliente.")
                .build());


        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorSlider2)
                .buttonsColor(R.color.colorButton2)
                .image(R.mipmap.slide2)
                .title("Detalhes sobre o aplicativo")
                .description("No Star Store é possivel visualizar itens como, roupas e acessorios. Você tambem pode adicionar item no carrinho para futuramente enfetuar a compra." +
                        "Pesquisar de forma facil.")
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorSlider3)
                .buttonsColor(R.color.colorButton3)
                .image(R.mipmap.slide3)
                .title("Historico")
                .description("Com o Star Store é possivel visualizar suas compras feita no app, com respectivas datas. " +
                        " Fique a vontade para compartilhar o que quiser !")
                .build());

        addSlide(new SlideFragmentBuilder()
                .image(R.mipmap.slide4
                )
                .backgroundColor(R.color.colorSlider4)
                .buttonsColor(R.color.colorButton4)
                .title("Sugestões, Duvidas, Criticas")
                .description("Acesso a area de fedbeeck para melhorias no Star Store.")
                .build());
    }
    @Override
    public void onFinish() {
        super.onFinish();

        prefManager.edit().putBoolean("isFirstTimeLaunch",false).apply();

        startActivity(new Intent(IntroActivity.this, MainActivity.class));
        finish();
    }
//    private void launchHomeScreen() {
//        prefManager.setFirstTimeLaunch(false);
//        startActivity(new Intent(IntroActivity.this, LoginActivity.class));
//        finish();
//    }



}