package com.example.david_k.oneStopClick.Views.Fragments.PaymentDetail;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.david_k.oneStopClick.Helper.Constants;
import com.example.david_k.oneStopClick.ModelLayers.Database.ProductCart;
import com.example.david_k.oneStopClick.ModelLayers.Enums.PaymentMethod;
import com.example.david_k.oneStopClick.R;
import com.example.david_k.oneStopClick.Views.Activities.PaymentMethods.CashOnDeliveryPaymentActivity;
import com.example.david_k.oneStopClick.Views.Activities.PaymentMethods.CreditCartPaymentActivity;
import com.example.david_k.oneStopClick.Views.Activities.PaymentMethods.PaypalPaymentActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentMethodFragment extends Fragment {

    RadioGroup radioGroup;
    ProductCart productCartFromBundle;

    public PaymentMethodFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_payment_detail_payment_method, container, false);

        productCartFromBundle = getActivity().getIntent().getExtras().getParcelable(Constants.productCartKey);

        Button nextButton = (Button) rootView.findViewById(R.id.payment_method_next_button);
        nextButton.setOnClickListener(v -> {

            PaymentMethod selectedPayment;
            try {
                selectedPayment = PaymentMethod.fromViewId(radioGroup.getCheckedRadioButtonId());
            }
            catch (IllegalStateException ex){

                ErrorNextPaymentMethodDialogFragment errorSaveDialogFragment = new ErrorNextPaymentMethodDialogFragment();
                errorSaveDialogFragment.show(getFragmentManager(), "PaymentMethodFragment");
                return;
            }

            Intent intent;

            switch (selectedPayment) {
                case CC:
                    intent = new Intent(getActivity(), CreditCartPaymentActivity.class);
                    intent.putExtra(Constants.productCartKey, productCartFromBundle);
                    startActivity(intent);
                    break;
                case COD:
                    intent = new Intent(getActivity(), CashOnDeliveryPaymentActivity.class);
                    intent.putExtra(Constants.productCartKey, productCartFromBundle);
                    startActivity(intent);
                    break;
                case PAYPAL:
                    intent = new Intent(getActivity(), PaypalPaymentActivity.class);
                    intent.putExtra(Constants.productCartKey, productCartFromBundle);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        });

        radioGroup = (RadioGroup) rootView.findViewById(R.id.payment_method_radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                setSelectedPaymentMethodToRepository(checkedId);
            }

            private void setSelectedPaymentMethodToRepository(int selectedPaymentMethodId){
                PaymentMethod selectedPaymentMethod = PaymentMethod.fromViewId(selectedPaymentMethodId);
                Log.d("PaymentMethodFragment", "setSelectedPaymentMethodToRepository: " + selectedPaymentMethod);
            }
        });

        return rootView;
    }


    public static class ErrorNextPaymentMethodDialogFragment extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("One of the Payment method should be selected.")
                    .setNegativeButton("Ok", (dialog, which) -> dialog.cancel());

            return builder.create();
        }
    }
}
