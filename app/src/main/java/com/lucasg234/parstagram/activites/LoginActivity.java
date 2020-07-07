package com.lucasg234.parstagram.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lucasg234.parstagram.databinding.ActivityLoginBinding;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    
    private ActivityLoginBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Skip the login activity if the user is already signed in
        if(ParseUser.getCurrentUser() != null) {
            startMainActivity();
        }

        mBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mBinding.loginUsername.getText().toString();
                String password = mBinding.loginPassword.getText().toString();
                loginUser(username, password);
            }
        });
    }

    private void loginUser(String username, String password) {
        Log.i(TAG, "Attempting to log in user: " + username);
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e != null) {
                    Log.e(TAG, "Error logging in", e);
                    Toast.makeText(LoginActivity.this,
                            "Error with login. Please check username/password and try again.", Toast.LENGTH_SHORT).show();
                    return;
                }
                startMainActivity();
            }
        });
    }

    private void startMainActivity() {
        Intent mainActivityItent = new Intent(this, MainActivity.class);
        startActivity(mainActivityItent);
        finish();
    }
}