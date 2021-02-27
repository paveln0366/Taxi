package com.pavelpotapov.taxi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PassengerSignInActivity extends AppCompatActivity {
    private static final String TAG = "PassengerSignInActivity";
    private TextInputLayout tiEmail;
    private TextInputLayout tiName;
    private TextInputLayout tiPassword;
    private TextInputLayout tiConfirmPassword;
    private Button btnSingUp;
    private TextView tvToLogIn;
    private boolean isLoginModeActive;
    private FirebaseAuth auth;

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
        auth = FirebaseAuth.getInstance();
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

        if (passwordInput.isEmpty()) {
            tiPassword.setError("Input your password");
            return false;
        } else if (passwordInput.length() < 7) {
            tiPassword.setError("Password length have to be most than 6");
            return false;
        } else {
            tiPassword.setError("");
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        String passwordInput = tiPassword.getEditText().getText().toString().trim();
        String confirmPasswordInput = tiConfirmPassword.getEditText().getText().toString().trim();

        if (!passwordInput.equals(confirmPasswordInput)) {
            tiPassword.setError("Passwords have to match");
            return false;
        } else {
            tiPassword.setError("");
            return true;
        }
    }

    public void onClickSingUp(View view) {
        String emailInput = tiEmail.getEditText().getText().toString().trim();
        String passwordInput = tiPassword.getEditText().getText().toString().trim();

        if (!validateEmail() | !validateName() | !validatePassword()) {
            return;
        }

        if (isLoginModeActive) {
            auth.signInWithEmailAndPassword(emailInput, passwordInput)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = auth.getCurrentUser();
//                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(PassengerSignInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
//                                updateUI(null);
                                // ...
                            }

                            // ...
                        }
                    });
        } else {

            if (!validateEmail() | !validateName() | !validatePassword() | !validateConfirmPassword()) {
                return;
            }

            auth.createUserWithEmailAndPassword(emailInput, passwordInput)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = auth.getCurrentUser();
//                            updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(PassengerSignInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                            }

                            // ...
                        }
                    });
        }

    }

    public void onClickTapToLogIn(View view) {
        if (isLoginModeActive) {
            isLoginModeActive = false;
            btnSingUp.setText("Sign up");
            tvToLogIn.setText("Or, log in");
            tiConfirmPassword.setVisibility(View.VISIBLE);
        } else {
            isLoginModeActive = true;
            btnSingUp.setText("Log in");
            tvToLogIn.setText("Or, sign up");
            tiConfirmPassword.setVisibility(View.GONE);
        }
    }
}