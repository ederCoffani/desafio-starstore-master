//package br.com.coffani.starstore.helper;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.SharedPreferences;
//
///**
// * Created by Danielopes97 on 19/05/2017.
// */
//
//public class PreferenciasAndroid {
//
//    SharedPreferences pref;
//
//    private Context context;
//    private SharedPreferences preferences;
//    private String NOME_ARQUIVO = "starStore.preferencias";
//    private int MODE = 0;
//    private SharedPreferences.Editor editor;
//
//
//    public static final String FILER_NAME = "MyFileName";
//    private final String CHAVE_IDENTIFICADOR = "identificarUsuarioLogado";
//    private final String CHAVE_NOME = "nomeUsuarioLogado";
//    // Shared preferences file name
//    private static final String PREF_NAME = "androidhive-welcome";
//
//    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
//    // shared pref mode
//    int PRIVATE_MODE = 0;
//
//
////    public PrefManager(Context context) {
////        this._context = context;
////        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
////        editor = pref.edit();
////    }
//
//    public void setFirstTimeLaunch(boolean isFirstTime) {
//        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
//        editor.commit();
//    }
//
//    public boolean isFirstTimeLaunch() {
//        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
//    }
//
//    @SuppressLint("CommitPrefEdits")
//    public PreferenciasAndroid(Context context) {
//        this.context = context;
//        preferences = context.getSharedPreferences(NOME_ARQUIVO, MODE);
//        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
//        editor = preferences.edit();
//        editor = pref.edit();
//    }
//
//    public void salvarUsuarioPrefencias(String idenficadorUsuario, String nomeUsuario) {
//        editor.putString(CHAVE_IDENTIFICADOR, idenficadorUsuario);
//        editor.putString(CHAVE_NOME, nomeUsuario);
//        editor.commit();
//    }
//
//    public String getIdenficador() {
//
//        return preferences.getString(CHAVE_IDENTIFICADOR, null);
//    }
//
//    public String getNome() {
//
//        return preferences.getString(CHAVE_NOME, null);
//    }
//    public static String readSharedSetting(Context ctx, String settingName, String defaultValue) {
//        SharedPreferences sharedPref = ctx.getSharedPreferences(FILER_NAME, Context.MODE_PRIVATE);
//        return sharedPref.getString(settingName, defaultValue);
//    }
//
//    public static void saveSharedSetting(Context ctx, String settingName, String settingValue) {
//        SharedPreferences sharedPref = ctx.getSharedPreferences(FILER_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putString(settingName, settingValue);
//        editor.apply();
//    }
//    public static void SharedPrefesSAVE(Context ctx,String Name){
//        SharedPreferences prefs = ctx.getSharedPreferences("NAME", 0);
//        SharedPreferences.Editor prefEDIT = prefs.edit();
//        prefEDIT.putString("Name", Name);
//        prefEDIT.commit();
//    }
//}
