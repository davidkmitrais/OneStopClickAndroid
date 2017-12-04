package com.example.david_k.oneStopClick.Views.Activities.LoginAuthentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.example.david_k.oneStopClick.MainActivity;
import com.example.david_k.oneStopClick.R;
import com.google.firebase.auth.FirebaseAuth;

public class AuthenticationActivity extends AppCompatActivity {

    private static final String TAG = "AuthenticationActivity";
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();

        Button loginAsGuest = findViewById(R.id.guest_login);
        loginAsGuest.setOnClickListener(view -> {

            Log.d(TAG, "onClick: Login As Guest Clicked");

            firebaseAuth.signInAnonymously().addOnCompleteListener(task -> {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            });
        });

        Button loginWithAccount = findViewById(R.id.account_login);
        loginWithAccount.setOnClickListener(view -> {
            Log.d(TAG, "onClick: Login with Account Clicked");

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });
    }
}
