package com.example.david_k.oneStopClick.Views.Activities.PaymentDetail;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.david_k.oneStopClick.ModelLayers.CenterRepository;
import com.example.david_k.oneStopClick.ModelLayers.Database.Address;
import com.example.david_k.oneStopClick.R;
import com.example.david_k.oneStopClick.Views.Fragments.PaymentDetail.ConfirmationPaymentFragment;
import com.example.david_k.oneStopClick.Views.Fragments.PaymentDetail.SelectAddressFragment;

public class PaymentDetailTabActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

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
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

            private void setupSelectedAddressTexts() {
                Address selectedAddress = CenterRepository.getCenterRepository().getSelectedAddress();

                FragmentManager fragmentManager = getSupportFragmentManager();
                ConfirmationPaymentFragment confirmationFragment = (ConfirmationPaymentFragment) fragmentManager.findFragmentById(R.id.confirmation_payment_fragment);
                View confirmationFragView = confirmationFragment.getView();

                TextView addressDeliveryText = (TextView)confirmationFragView.findViewById(R.id.address_detail_delivery_confirmation);
                TextView addressCityText = (TextView)confirmationFragView.findViewById(R.id.address_detail_city_confirmation);
                TextView addressStateText = (TextView)confirmationFragView.findViewById(R.id.address_detail_state_confirmation);
                Button backToAdressButton = (Button)confirmationFragView.findViewById(R.id.back_to_select_address_conf_button);
                Button continueToPaymentButton = (Button)confirmationFragView.findViewById(R.id.continue_to_payment_conf_button);

                if (selectedAddress != null) {
                    addressDeliveryText.setText(selectedAddress.getDeliveryAddress());
                    addressCityText.setText(selectedAddress.getCity());
                    addressStateText.setText(selectedAddress.getState());
                    backToAdressButton.setVisibility(View.INVISIBLE);
                    continueToPaymentButton.setVisibility(View.VISIBLE);
                }
                else {
                    addressDeliveryText.setText("Please select an address before continue to payment.");
                    addressCityText.setText("");
                    addressStateText.setText("");
                    continueToPaymentButton.setVisibility(View.INVISIBLE);
                    backToAdressButton.setVisibility(View.VISIBLE);
                }
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
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_payment_tab, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            Address selectedAddress = CenterRepository.getCenterRepository().getSelectedAddress();
            String addressName;
            if (selectedAddress == null) {
                addressName = "NOT SET";
            } else {
                addressName = selectedAddress.getAddressName();
            }
            Log.d("PayDetailTabActivity", "Selected Address is " + addressName);
            return rootView;
        }
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

                default:
                    // getItem is called to instantiate the fragment for the given page.
                    // Return a PlaceholderFragment (defined as a static inner class below).
                    return PlaceholderFragment.newInstance(position + 1);
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
                    return "Payment";
            }
            return null;
        }
    }
}
