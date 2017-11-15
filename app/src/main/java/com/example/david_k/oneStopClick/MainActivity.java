package com.example.david_k.oneStopClick;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.david_k.oneStopClick.Database.DataSource;
import com.example.david_k.oneStopClick.Helper.CenterRepositoryHelper;
import com.example.david_k.oneStopClick.Helper.Constants;
import com.example.david_k.oneStopClick.ModelLayers.CenterRepository;
import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.ModelLayers.SampleProductProvider;
import com.example.david_k.oneStopClick.Views.Fragments.About.AboutFragment;
import com.example.david_k.oneStopClick.Views.Fragments.Admin.AdminFragment;
import com.example.david_k.oneStopClick.Views.Fragments.Cart.CartFragment;
import com.example.david_k.oneStopClick.Views.Fragments.ProductList.ProductListFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DataSource mDataSource;
    private List<Product> productList = SampleProductProvider.productList;
    CenterRepositoryHelper centerRepositoryHelper = new CenterRepositoryHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        SetupProductData();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setFragmentDisplay();
    }

    private void setFragmentDisplay() {
        Bundle bundle = getIntent().getExtras();
        boolean goToChartFragment = false;
        if (bundle != null){
            goToChartFragment = bundle.getBoolean(Constants.mainActivityGoToCartFragmentBool, false);
        }

        if (!goToChartFragment){
            return;
        }

        Class fragmentClass = CartFragment.class;
        Fragment fragment = null;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.screen_area, fragment).commit();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;

        switch (id) {
            case R.id.nav_product:
                fragmentClass = ProductListFragment.class;
                break;
            case R.id.nav_chart:
                fragmentClass = CartFragment.class;
                break;
            case R.id.nav_admin:
                fragmentClass = AdminFragment.class;
                break;
            case R.id.nav_about:
                fragmentClass = AboutFragment.class;
                break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.screen_area, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        //mDataSource.close();
    }

    @Override
    public void onPause() {
        super.onPause();
        //mDataSource.open();
    }

    //region Database Setup
    //TODO: need to be deleted
    private void SetupProductData(){

        mDataSource = new DataSource(this);
        mDataSource.open();
        mDataSource.seedDatabase(productList);

        List<Product> productListFromDB = mDataSource.getAllItems();

        if (!centerRepositoryHelper.IsProductSetOnCenterRepository()) {
            CenterRepository.getCenterRepository().setListOfProductsInShoppingList(productListFromDB);
            CenterRepository.getCenterRepository().setDummyAddressDefault();
        }
    }
    //endregion
}
