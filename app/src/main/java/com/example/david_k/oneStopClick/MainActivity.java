package com.example.david_k.oneStopClick;

import android.content.Intent;
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
import android.view.View;
import android.widget.TextView;

import com.example.david_k.oneStopClick.Helper.Constants;
import com.example.david_k.oneStopClick.Views.Activities.LoginAuthentication.AuthenticationActivity;
import com.example.david_k.oneStopClick.Views.Fragments.About.AboutFragment;
import com.example.david_k.oneStopClick.Views.Fragments.Admin.AdminFragment;
import com.example.david_k.oneStopClick.Views.Fragments.Cart.CartFragment;
import com.example.david_k.oneStopClick.Views.Fragments.ProductList.ProductListFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setUserInfo(navigationView.getHeaderView(0));

        setFragmentDisplay();
    }

    private void setUserInfo(View headerView) {
        TextView line1 = (TextView) headerView.findViewById(R.id.user_info_text_line1);
        TextView line2 = (TextView) headerView.findViewById(R.id.user_info_text_line2);

        if (user != null && !user.isAnonymous()) {
            line1.setText(user.getDisplayName());
            line2.setText(user.getEmail());
        } else {
            line1.setText("Guest");
            line2.setText("");
        }
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
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_logout:
                auth.signOut();
                Intent loginIntent = new Intent(this, AuthenticationActivity.class);
                startActivity(loginIntent);

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
}
