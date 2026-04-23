package com.example.istreamapp.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.istreamapp.R;
import com.example.istreamapp.database.AppDatabase;
import com.example.istreamapp.database.User;

public class SignUpActivity extends AppCompatActivity {

    private EditText etFullName, etNewUsername, etNewPassword, etConfirmPassword;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        db = AppDatabase.getInstance(this);

        etFullName = findViewById(R.id.etFullName);
        etNewUsername = findViewById(R.id.etNewUsername);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        Button btnCreateAccount = findViewById(R.id.btnCreateAccount);

        btnCreateAccount.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String fullName = etFullName.getText().toString().trim();
        String username = etNewUsername.getText().toString().trim();
        String password = etNewPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (fullName.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (db.userDao().getUserByUsername(username) != null) {
            Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        db.userDao().insertUser(new User(fullName, username, password));
        Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}