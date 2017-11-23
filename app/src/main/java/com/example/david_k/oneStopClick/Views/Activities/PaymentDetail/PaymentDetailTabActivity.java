package com.example.david_k.oneStopClick.Views.Activities.PaymentDetail;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.david_k.oneStopClick.Firebase.FirebaseProvider;
import com.example.david_k.oneStopClick.Helper.Constants;
import com.example.david_k.oneStopClick.Helper.FirebaseProviderHelper;
import com.example.david_k.oneStopClick.Helper.OnGetDataListener;
import com.example.david_k.oneStopClick.ModelLayers.Database.Address;
import com.example.david_k.oneStopClick.R;
import com.example.david_k.oneStopClick.Views.Fragments.PaymentDetail.ConfirmationPaymentFragment;
import com.example.david_k.oneStopClick.Views.Fragments.PaymentDetail.PaymentMethodFragment;
import com.example.david_k.oneStopClick.Views.Fragments.PaymentDetail.SelectAddressFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class PaymentDetailTabActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    FirebaseProviderHelper firebaseProviderHelper = new FirebaseProviderHelper();

    private ViewPager mViewPager;
    Button confBackToAdressButton;
    Button confContinueToPaymentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_tab_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.payment_tab_view_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            TextView addressDeliveryText;
            TextView addressCityText;
            TextView addressStateText;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 1:
                        setupSelectedAddressTexts();
                        break;
                }
            }

            private void setupConfirmationFragmentView(){
                FragmentManager fragmentManager = getSupportFragmentManager();
                ConfirmationPaymentFragment confirmationFragment = (ConfirmationPaymentFragment) fragmentManager.findFragmentById(R.id.confirmation_payment_fragment);
                View confirmationFragView = confirmationFragment.getView();

                addressDeliveryText = (TextView)confirmationFragView.findViewById(R.id.address_detail_delivery_confirmation);
                addressCityText = (TextView)confirmationFragView.findViewById(R.id.address_detail_city_confirmation);
                addressStateText = (TextView)confirmationFragView.findViewById(R.id.address_detail_state_confirmation);
                confBackToAdressButton = (Button)confirmationFragView.findViewById(R.id.back_to_select_address_conf_button);
                confContinueToPaymentButton = (Button)confirmationFragView.findViewById(R.id.continue_to_payment_conf_button);

                confBackToAdressButton.setOnClickListener(v ->
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1));

                confContinueToPaymentButton.setOnClickListener(v ->
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1));
            }

            private void setupSelectedAddressTexts() {

                DatabaseReference addressDbRef = FirebaseProvider.getCurrentProvider().getAddressDBReference()
                        .child(Address.CHILD_SELECTED_ADDRESS);

                firebaseProviderHelper.getDataSnapshotOnceFromDBRef(addressDbRef, new OnGetDataListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(DataSnapshot data) {
                        String addressKey = data.getValue(String.class);

                        setupConfirmationFragmentView();

                        if (addressKey.equals(Constants.notSetKey)){
                            addressDeliveryText.setText("Please select an address before continue to payment.");
                            addressCityText.setText("");
                            addressStateText.setText("");
                            confContinueToPaymentButton.setVisibility(View.INVISIBLE);
                            confBackToAdressButton.setVisibility(View.VISIBLE);
                        }
                        else {
                            Query selectedAddressQuery = FirebaseProvider.getCurrentProvider().getAddressDBReference()
                                    .child(Address.CHILD_ADDRESS_LIST).orderByKey().equalTo(addressKey);

                            firebaseProviderHelper.getDataSnapshotOnceFromQuery(selectedAddressQuery, new OnGetDataListener() {
                                @Override
                                public void onStart() {

                                }

                                @Override
                                public void onSuccess(DataSnapshot data) {

                                    for (DataSnapshot dataItem: data.getChildren()) {
                                        Address selectedAddress;
                                        selectedAddress = dataItem.getValue(Address.class);

                                        addressDeliveryText.setText(selectedAddress.getDeliveryAddress());
                                        addressCityText.setText(selectedAddress.getCity());
                                        addressStateText.setText(selectedAddress.getState());
                                        confBackToAdressButton.setVisibility(View.INVISIBLE);
                                        confContinueToPaymentButton.setVisibility(View.VISIBLE);
                                    }
                                }

                                @Override
                                public void onFailed(DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailed(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_payment_tab_detail, menu);
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

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new SelectAddressFragment();

                case 1:
                    getSupportFragmentManager().beginTransaction().add(R.id.confirmation_payment_fragment, new ConfirmationPaymentFragment(), "confirmation_payment_fragment").commit();
                    return new ConfirmationPaymentFragment();

                case 2:
                    return new PaymentMethodFragment();

                default:
                    return new SelectAddressFragment();
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Select Adress";
                case 1:
                    return "Confirmation";
                case 2:
                    return "Payment Method";
            }
            return null;
        }
    }
}
