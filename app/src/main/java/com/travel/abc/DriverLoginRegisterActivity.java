package com.travel.abc;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DriverLoginRegisterActivity extends AppCompatActivity {

    private Button DriverLogin;
    private Button DriverRegister;
    private TextView Driver;
    private TextView RegisterDriverLink;
    private EditText DriverEmail;
    private EditText DriverPassword;
    private ProgressDialog loadingbar;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login_register);

        mAuth = FirebaseAuth.getInstance();

        DriverLogin = (Button) findViewById(R.id.driver_login);
        DriverRegister = (Button) findViewById(R.id.driver_register);
        Driver = (TextView) findViewById(R.id.Driver);
        RegisterDriverLink = (TextView) findViewById(R.id.register_driver_link);
        DriverEmail = (EditText) findViewById(R.id.email_driver);
        DriverPassword = (EditText) findViewById(R.id.password_driver);
        loadingbar = new ProgressDialog(this);

        DriverRegister.setVisibility(View.INVISIBLE);
        DriverRegister.setEnabled(false);

        RegisterDriverLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DriverLogin.setVisibility(view.INVISIBLE);
                RegisterDriverLink.setVisibility(view.INVISIBLE);
                Driver.setText("Register Customer");
                DriverRegister.setVisibility(view.VISIBLE);
                DriverRegister.setEnabled(true);
            }
        });

        DriverRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
             String email = DriverEmail.getText().toString();
             String password = DriverPassword.getText().toString();

             DriverRegister (email,password);
            }
        });
        DriverLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String email = DriverEmail.getText().toString();
                String password = DriverPassword.getText().toString();

                DriverSignIN(email,password);

            }
        });
    }

    private void DriverSignIN(String email, String password)
    {
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(DriverLoginRegisterActivity.this,"Please enter your Email.",Toast.LENGTH_LONG).show();
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(DriverLoginRegisterActivity.this,"Please enter your Password.",Toast.LENGTH_LONG).show();
        }
        else
        {
            loadingbar.setTitle("Customer Registration");
            loadingbar.setMessage("Please wait! while we are checking your credentials");
            loadingbar.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(DriverLoginRegisterActivity.this,"Driver Login Successful!",Toast.LENGTH_LONG).show();
                        loadingbar.dismiss();
                    }
                    else
                    {
                        Toast.makeText(DriverLoginRegisterActivity.this, "Login failled! Please try again.", Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }
                }
            });
        }
    }

    private void DriverRegister(String email, String password)
    {
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(DriverLoginRegisterActivity.this,"Please enter your Email.",Toast.LENGTH_LONG).show();
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(DriverLoginRegisterActivity.this,"Please enter your Password.",Toast.LENGTH_LONG).show();
        }
        else
            {
             loadingbar.setTitle("Driver Registration");
             loadingbar.setMessage("Please wait! while we are registering your data.");
             loadingbar.show();

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(DriverLoginRegisterActivity.this,"Driver Register Successful!",Toast.LENGTH_LONG).show();
                            loadingbar.dismiss();
                        }
                        else
                        {
                            Toast.makeText(DriverLoginRegisterActivity.this, "Registration failled! Please try again.", Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                        }
                    }
                });
            }
    }
}
