package com.example.david_k.oneStopClick;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.david_k.oneStopClick.ModelLayers.CenterRepository;
import com.example.david_k.oneStopClick.ModelLayers.Database.Address;

public class AddAddressActivity extends AppCompatActivity {

    private final static String TAG = "AddAddressActivity";

    Button addAddress;
    EditText addressNameText;
    EditText deliveryText;
    EditText cityText;
    EditText stateText;

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
                }

            }
        });
    }

    private void setupUI(){

        addAddress = (Button) findViewById(R.id.save_address_buton);
        addressNameText = (EditText) findViewById(R.id.name_address_input);
        deliveryText = (EditText) findViewById(R.id.delivery_address_input);
        cityText = (EditText) findViewById(R.id.city_address_input);
        stateText = (EditText) findViewById(R.id.state_address_input);
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
