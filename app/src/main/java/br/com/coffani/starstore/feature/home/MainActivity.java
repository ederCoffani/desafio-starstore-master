package br.com.coffani.starstore.feature.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.coffani.starstore.R;
import br.com.coffani.starstore.adapter.StoreAdapter;
import br.com.coffani.starstore.base.mvp.MvpActivity;
import br.com.coffani.starstore.domain.Product;
import br.com.coffani.starstore.feature.historic.HistoricActivity;
import br.com.coffani.starstore.feature.introduction.IntroActivity;
import br.com.coffani.starstore.feature.payment.PaymentActivity;
import butterknife.ButterKnife;

public class MainActivity extends MvpActivity<MainPresenter> implements MainView, NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "STORE";
    public List<Product> pList;
    private String query = "products";
    private RecyclerView recyclerView;
    private StoreAdapter storeAdapter;
    private DrawerLayout drawer;


    private ProgressDialog mProgressDialog;

    @Override
    protected MainPresenter createPresenter() {

        return new MainPresenter(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initViews();
        presenter.loadData(query);

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
    public void initViews(){
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_carrinho) {
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
    public List<Product> getDataSuccess(List<Product> pList) {
        this.pList = pList;
        storeAdapter = new StoreAdapter(this, (ArrayList<Product>) pList);
        recyclerView.setAdapter(storeAdapter);
        storeAdapter.adicionarListaLoja((ArrayList<Product>) pList);
        return pList;
    }
    @Override
    public void getDataFail(String message) {
        Log.e(TAG, message);
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

            case R.id.nav_historic_shopping_db: {
                Toast.makeText(getApplicationContext(), "Historicos", Toast.LENGTH_SHORT).show();
                Intent it =new Intent(getApplicationContext(), HistoricActivity.class);
                startActivity(it);
                break;
            }
            case R.id.menu_carrinho: {
                Toast.makeText(getApplicationContext(), "Carrinho", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, PaymentActivity.class));

                break;
            }
            case R.id.intro: {
                Intent it =new Intent(getApplicationContext(), IntroActivity.class);
                startActivity(it);
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

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
}

