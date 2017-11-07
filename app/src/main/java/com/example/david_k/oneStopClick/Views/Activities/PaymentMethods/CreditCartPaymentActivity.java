package com.example.david_k.oneStopClick.Views.Activities.PaymentMethods;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.david_k.oneStopClick.Helper.Helper;
import com.example.david_k.oneStopClick.ModelLayers.CenterRepository;
import com.example.david_k.oneStopClick.R;

public class CreditCartPaymentActivity extends AppCompatActivity {

    private final static String TAG = "CCPaymentActivity";

    Button ccConfirmButton;
    EditText ccFullNameText;
    EditText ccNumberText;
    EditText ccExpDateText;
    EditText ccCVVText;
    private ErrorSaveCCPaymentDialogFragment errorSaveDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_cart_payment);

        setupUI();

        ccConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateAdressInputText()) {
                    Log.d(TAG, "Validation error!", null);

                    errorSaveDialogFragment = new ErrorSaveCCPaymentDialogFragment();
                    errorSaveDialogFragment.show(getSupportFragmentManager(), TAG);
                }
                else {
//                    Address newAddress = new Address(
//                            addressNameText.getText().toString(),
//                            deliveryText.getText().toString(),
//                            cityText.getText().toString(),
//                            stateText.getText().toString()
//                    );
//
//                    CenterRepository.getCenterRepository().addToAddressList(newAddress);

                    Log.d(TAG, "Validation Success, new order " + CenterRepository.getCenterRepository().getSelectedProduct().getName() + " added into repo", null);

//                    goToSelectAddressListActivity();
                }
            }
        });

    }

    private void setupUI(){

        ccConfirmButton = (Button) findViewById(R.id.cc_confirm_button);
        ccFullNameText = (EditText) findViewById(R.id.cc_full_name_input);
        ccNumberText = (EditText) findViewById(R.id.cc_number_input);
        ccExpDateText = (EditText) findViewById(R.id.cc_exp_date_number_input);
        ccCVVText = (EditText) findViewById(R.id.cc_cvv_input);
    }

    private boolean validateAdressInputText(){
        if (Helper.editTextIsEmptyOrNull(ccFullNameText)
                || Helper.editTextIsEmptyOrNull(ccNumberText)
                || Helper.editTextIsEmptyOrNull(ccExpDateText)
                || Helper.editTextIsEmptyOrNull(ccCVVText)){

            return false;
        }

        return true;
    }

    public static class ErrorSaveCCPaymentDialogFragment extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("All Credit Card detail must be filled.")
                    .setNegativeButton("OK", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            return builder.create();
        }
    }

}
