package com.pavelpotapov.taxi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class PassengerSignInActivity extends AppCompatActivity {

    private TextInputLayout tiEmail;
    private TextInputLayout tiName;
    private TextInputLayout tiPassword;
    private TextInputLayout tiConfirmPassword;

    private Button btnSingUp;
    private TextView tvToLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_sign_in);

        tiEmail = findViewById(R.id.ti_email);
        tiName = findViewById(R.id.ti_name);
        tiPassword = findViewById(R.id.ti_password);
        tiConfirmPassword = findViewById(R.id.ti_confirm_password);
        btnSingUp = findViewById(R.id.btn_sign_up);
        tvToLogIn = findViewById(R.id.tv_to_log_in);
    }

    private boolean validateEmail() {
        String emailInput = tiEmail.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            tiEmail.setError("Input your email");
            return false;
        } else {
            tiEmail.setError("");
            return true;
        }
    }

    private boolean validateName() {
        String nameInput = tiName.getEditText().getText().toString().trim();
        if (nameInput.isEmpty()) {
            tiName.setError("Input your name");
            return false;
        } else if (nameInput.length() > 15) {
            tiName.setError("Name length have to be less than 15");
            return false;
        } else {
            tiName.setError("");
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = tiPassword.getEditText().getText().toString().trim();
        String confirmPasswordInput = tiConfirmPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            tiPassword.setError("Input your password");
            return false;
        } else if (passwordInput.length() < 7) {
            tiPassword.setError("Password length have to be most than 6");
            return false;
        } else if (!passwordInput.equals(confirmPasswordInput)) {
            tiPassword.setError("Passwords have to match");
            return false;
        } else {
            tiPassword.setError("");
            return true;
        }
    }

    public void onClickSingUp(View view) {
        if (!validateEmail() | !validateName() | !validatePassword()) {
            return;
        }

        String userInput = "Email: " + tiEmail.getEditText().getText().toString().trim() +
                "\n" + "Name: " + tiName.getEditText().getText().toString().trim() +
                "\n" + "Password: " + tiPassword.getEditText().getText().toString().trim();

        Toast.makeText(this, userInput, Toast.LENGTH_LONG).show();
    }

    public void onClickTapToLogIn(View view) {
    }
}