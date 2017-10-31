package com.example.david_k.oneStopClick.Views.Activities.PaymentAddAddress;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.IDNA;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.david_k.oneStopClick.Helper.Constants;
import com.example.david_k.oneStopClick.MainActivity;
import com.example.david_k.oneStopClick.ModelLayers.CenterRepository;
import com.example.david_k.oneStopClick.ModelLayers.Database.Address;
import com.example.david_k.oneStopClick.R;
import com.example.david_k.oneStopClick.Views.Activities.PaymentDetail.PaymentDetailTabActivity;
import com.example.david_k.oneStopClick.Views.Activities.ProductDetail.ProductDetailActivity;

public class PaymentAddAddressActivity extends AppCompatActivity {

    private final static String TAG = "AddAddressActivity";

    Button addAddress;
    EditText addressNameText;
    EditText deliveryText;
    EditText cityText;
    EditText stateText;
    private ErrorSaveAddedAddressDialogFragment errorSaveDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        setupUI();

        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateAdressInputText()) {
                    Log.d(TAG, "Validation error!", null);

                    errorSaveDialogFragment = new ErrorSaveAddedAddressDialogFragment();
                    errorSaveDialogFragment.show(getSupportFragmentManager(), TAG);
                }
                else {
                    Address newAddress = new Address(
                            addressNameText.getText().toString(),
                            deliveryText.getText().toString(),
                            cityText.getText().toString(),
                            stateText.getText().toString()
                    );

                    CenterRepository.getCenterRepository().addToAddressList(newAddress);

                    Log.d(TAG, "Validation Success, new address " + newAddress.getAddressName() + " added into repo", null);

                    goToSelectAddressListActivity();
                }

            }
        });

        setupEditTextsValidation();
    }

    private void setupUI(){

        addAddress = (Button) findViewById(R.id.save_address_buton);
        addressNameText = (EditText) findViewById(R.id.name_address_input);
        deliveryText = (EditText) findViewById(R.id.delivery_address_input);
        cityText = (EditText) findViewById(R.id.city_address_input);
        stateText = (EditText) findViewById(R.id.state_address_input);
    }

    private void setupEditTextsValidation(){
        setOnFocusChangeValidation(addressNameText);
        setOnFocusChangeValidation(deliveryText);
        setOnFocusChangeValidation(cityText);
        setOnFocusChangeValidation(stateText);
    }

    private void setOnFocusChangeValidation(EditText editText){

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (editText.getText().length() < 3) {
                    editText.setError("Should be filled with minimum 3 characters");
                }
            }
        });
    }

    private void goToSelectAddressListActivity() {
        Bundle bundle = new Bundle();
//        bundle.putBoolean(Constants.mainActivityGoToCartFragmentBool, true);

        Intent intent = new Intent(this, PaymentDetailTabActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    public static class ErrorSaveAddedAddressDialogFragment extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("All detail must be filled.")
                    .setNegativeButton("Back to add new address", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            return builder.create();
        }
    }

    private boolean validateAdressInputText(){
        if (editTextIsEmptyOrNull(addressNameText)
                || editTextIsEmptyOrNull(deliveryText)
                || editTextIsEmptyOrNull(cityText)
                || editTextIsEmptyOrNull(stateText)){

            return false;
        }

        return true;
    }

    private boolean editTextIsEmptyOrNull(EditText editText){
        String text = editText.getText().toString();

        if (stringInputIsEmptyOrNull(text)){
            return true;
        }

        return false;
    }

    private boolean stringInputIsEmptyOrNull(String input) {
        if (input.equals(null) || input.equals("")) {
            return true;
        }

        return false;
    }
}
