package com.example.david_k.oneStopClick.Views.Activities.PaymentAddAddress;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.david_k.oneStopClick.Helper.Constants;
import com.example.david_k.oneStopClick.Helper.FirebaseProviderHelper;
import com.example.david_k.oneStopClick.Helper.Helper;
import com.example.david_k.oneStopClick.ModelLayers.Database.Address;
import com.example.david_k.oneStopClick.ModelLayers.Database.ProductCart;
import com.example.david_k.oneStopClick.R;
import com.example.david_k.oneStopClick.Views.Activities.PaymentDetail.PaymentDetailTabActivity;

public class PaymentAddAddressActivity extends AppCompatActivity {

    private final static String TAG = "AddAddressActivity";

    Button addAddress;
    EditText addressNameText;
    EditText deliveryText;
    EditText cityText;
    EditText stateText;
    ProductCart productCartFromBundle;
    private ErrorSaveAddedAddressDialogFragment errorSaveDialogFragment;
    private FirebaseProviderHelper firebaseProviderHelper = new FirebaseProviderHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        productCartFromBundle = this.getIntent().getExtras().getParcelable(Constants.productCartKey);

        setupUI();

        addAddress.setOnClickListener(v -> {

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

                firebaseProviderHelper.addNewAddressList(newAddress);

                Log.d(TAG, "Validation Success, new address " + newAddress.getAddressName() + " added into repo", null);

                goToSelectAddressListActivity();
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

        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (editText.getText().length() < 3) {
                editText.setError("Should be filled with minimum 3 characters");
            }
        });
    }

    private void goToSelectAddressListActivity() {
        Bundle bundle = new Bundle();

        Intent intent = new Intent(this, PaymentDetailTabActivity.class);
        intent.putExtra(Constants.productCartKey, productCartFromBundle);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    public static class ErrorSaveAddedAddressDialogFragment extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("All detail must be filled.")
                    .setNegativeButton("Back to add new address", (dialog, which) -> dialog.cancel());

            return builder.create();
        }
    }

    private boolean validateAdressInputText(){
        return !(Helper.editTextIsEmptyOrNull(addressNameText)
                || Helper.editTextIsEmptyOrNull(deliveryText)
                || Helper.editTextIsEmptyOrNull(cityText)
                || Helper.editTextIsEmptyOrNull(stateText));
    }
}
