package com.example.david_k.oneStopClick.View.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.david_k.oneStopClick.View.Adapter.ParentFragmentPagerAdapter;
import com.example.david_k.oneStopClick.R;
import com.example.david_k.oneStopClick.View.Fragment.AccountFragment;
import com.example.david_k.oneStopClick.View.Fragment.DashboardFragment;
import com.example.david_k.oneStopClick.View.Fragment.ProductFragment;

public class ParentActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_home_black_24dp,
            R.drawable.ic_menu_product,
            R.drawable.ic_menu_admin};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);

        toolbar = findViewById(R.id.toolbar_parent);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();

        viewPager = findViewById(R.id.viewpager_parent);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.parent_tab);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ParentFragmentPagerAdapter adapter = new ParentFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DashboardFragment(), "Dashboard");
        adapter.addFragment(new ProductFragment(), "Product");
        adapter.addFragment(new AccountFragment(), "Account");
        viewPager.setAdapter(adapter);
    }


}
