package br.com.coffani.starstore.feature.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import br.com.coffani.starstore.R;
import br.com.coffani.starstore.adapter.StoreAdapter;
import br.com.coffani.starstore.base.mvp.MvpActivity;
import br.com.coffani.starstore.database.DatabaseManagerUser;
import br.com.coffani.starstore.domain.Product;
import br.com.coffani.starstore.domain.User;
import br.com.coffani.starstore.feature.login.LoginActivity;
import br.com.coffani.starstore.feature.payment.PaymentActivity;
import br.com.coffani.starstore.firebase.NetworkConfigFirebase;
import br.com.coffani.starstore.helper.Base64Custom;
//import br.com.coffani.starstore.helper.PreferenciasAndroid;
import butterknife.ButterKnife;

public class MainActivity extends MvpActivity<MainPresenter> implements MainView, NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "LOJA";
    private String query = "products";
    private RecyclerView recyclerView;
    private StoreAdapter storeAdapter;
    public List<Product> pList;
    private DrawerLayout drawer;

    private DatabaseManagerUser databaseManagerUser;
    private User itemUsuario;
    private String ident;

    private ProgressDialog mProgressDialog;

    private TextView text;


    @Override
    protected MainPresenter createPresenter() {

        return new MainPresenter(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        checkUser();


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(false);

        final LinearLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                }
            }
        });
        presenter.loadData(query);

        //se agrego codigo del 39 al 68
        Bundle b = getIntent().getExtras();

        ident = b.getString("IDENT");

        databaseManagerUser = new DatabaseManagerUser(getApplicationContext());
        itemUsuario = databaseManagerUser.getUsuario(ident);

        View header = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0);

        ((TextView) header.findViewById(R.id.tv_nombre_usuario_menu)).setText(itemUsuario.getName());
        ((TextView) header.findViewById(R.id.tv_correo_menu)).setText(itemUsuario.getEmail());


//        String idenficadorUsuario = Base64Custom.codificarBase64(itemUsuario.getName());
//
//        PreferenciasAndroid preferenciasAndroid = new PreferenciasAndroid(MainActivity.this);
//        preferenciasAndroid.salvarUsuarioPrefencias(idenficadorUsuario, itemUsuario.getEmail());

        Bitmap bitmapsinfoto = BitmapFactory.decodeResource(getResources(), R.drawable.imagen);
        RoundedBitmapDrawable roundedBitmapDrawablesinfoto = RoundedBitmapDrawableFactory.create(getResources(), bitmapsinfoto);
        roundedBitmapDrawablesinfoto.setCircular(true);

        ((ImageView) header.findViewById(R.id.imageView)).setImageDrawable(roundedBitmapDrawablesinfoto);

        if (itemUsuario.getBytes() != null) {
            byte[] foodImage = itemUsuario.getBytes();
            Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);

            ((ImageView) header.findViewById(R.id.imageView)).setImageBitmap(bitmap);

            Bitmap bitmap2 = ((BitmapDrawable) ((ImageView) header.findViewById(R.id.imageView)).getDrawable()).getBitmap();
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap2);
            roundedBitmapDrawable.setCircular(true);

            ((ImageView) header.findViewById(R.id.imageView)).setImageDrawable(roundedBitmapDrawable);
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setNavigationViewListner();


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_carrinho) {
            startActivity(new Intent(MainActivity.this, PaymentActivity.class));
        }
        if (item.getItemId() == R.id.action_search) {
            startActivity(new Intent(MainActivity.this, PaymentActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoaging() {
        hideProgressDialog();
    }

    @Override
    public void getDataSuccess(List<Product> pList) {
        this.pList = pList;
        storeAdapter = new StoreAdapter(this, pList);
        recyclerView.setAdapter(storeAdapter);
        storeAdapter.adicionarListaLoja((ArrayList<Product>) pList);
    }

    @Override
    public void getDataFail(String message) {


    }

    @Override
    public void moveToDetail(Intent intent) {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {

            case R.id.nav_historic_shopping: {
                Toast.makeText(getApplicationContext(), "Historicos", Toast.LENGTH_SHORT).show();
//                Intent it =new Intent(getApplicationContext(), HistoricActivity.class);
//                startActivity(it);
                //do somthing
                break;
            }
            case R.id.login_firebase: {
//                logout();
                Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_SHORT).show();
//                Intent it = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(it);
                break;
            }
            case R.id.nav_config: {
//                Intent it =new Intent(getApplicationContext(), HistoricActivity.class);
//                startActivity(it);
                break;
            }
            case R.id.nav_privacy_policy: {
//                Intent it =new Intent(getApplicationContext(), HistoricActivity.class);
//                startActivity(it);
                break;
            }
        }
        //close navigation drawer


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    private void logout() {
//        PreferenciasAndroid.saveSharedSetting(MainActivity.this, "ClipCodes", "true");
//        PreferenciasAndroid.SharedPrefesSAVE(getApplicationContext(), "");
//        Intent LogOut = new Intent(getApplicationContext(), LoginActivity.class);
//        startActivity(LogOut);
//
//        finish();
//    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    private void setNavigationViewListner() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    protected void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    protected void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mProgressDialog.hide();
        super.onPause();

    }

//    public void checkUser() {
//
//
//        Boolean Check = Boolean.valueOf(PreferenciasAndroid.readSharedSetting(MainActivity.this, "ClipCodes", "true"));
//
//        Intent introIntent = new Intent(MainActivity.this, LoginActivity.class);
//        introIntent.putExtra("ClipCodes", Check);
//
//        if (Check) {
//            startActivity(introIntent);
//        }
//    }

}

