package com.travel.abc;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CustomerLoginRegisterActivity extends AppCompatActivity {
    private Button CustomerLogin;
    private Button CustomerRegister;
    private TextView Customer;
    private TextView RegisterCustomerLink;
    private EditText CustomerEmail;
    private EditText CustomerPassword;
    private ProgressDialog loadingbar;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login_register);

        mAuth = FirebaseAuth.getInstance();

        CustomerLogin = (Button) findViewById(R.id.customer_login);
        CustomerRegister = (Button) findViewById(R.id.customer_register);
        Customer = (TextView) findViewById(R.id.customer);
        RegisterCustomerLink = (TextView) findViewById(R.id.register_customer_link);
        CustomerEmail = (EditText) findViewById(R.id.email_customer);
        CustomerPassword = (EditText) findViewById(R.id.password_customer);
        loadingbar = new ProgressDialog(this);

        CustomerRegister.setVisibility(View.INVISIBLE);
        CustomerRegister.setEnabled(false);

        RegisterCustomerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomerLogin.setVisibility(view.INVISIBLE);
                RegisterCustomerLink.setVisibility(view.INVISIBLE);
                Customer.setText("Register Customer");
                CustomerRegister.setVisibility(view.VISIBLE);
                CustomerRegister.setEnabled(true);
            }
        });

        CustomerRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String email = CustomerEmail.getText().toString();
                String password = CustomerPassword.getText().toString();

                CustomerRegister (email,password);
            }
        });
        CustomerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) 
            {
                String email = CustomerEmail.getText().toString();
                String password = CustomerPassword.getText().toString();

                CustomerSignIN(email,password);
            }
        });
    }

    private void CustomerSignIN(String email, String password)
    {
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(CustomerLoginRegisterActivity.this,"Please enter your Email.",Toast.LENGTH_LONG).show();
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(CustomerLoginRegisterActivity.this,"Please enter your Password.",Toast.LENGTH_LONG).show();
        }
        else
        {
            loadingbar.setTitle("Customer Login");
            loadingbar.setMessage("Please wait! while we are checking your credentials.");
            loadingbar.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(CustomerLoginRegisterActivity.this,"Customer Login Successful!",Toast.LENGTH_LONG).show();
                        loadingbar.dismiss();
                    }
                    else
                    {
                        Toast.makeText(CustomerLoginRegisterActivity.this, "Login failled! Please try again.", Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }
                }
            });
        }

    }

    private void CustomerRegister(String email, String password)
    {
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(CustomerLoginRegisterActivity.this,"Please enter your Email.",Toast.LENGTH_LONG).show();
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(CustomerLoginRegisterActivity.this,"Please enter your Password.",Toast.LENGTH_LONG).show();
        }
        else
        {
            loadingbar.setTitle("Customer Registration");
            loadingbar.setMessage("Please wait! while we are registering your data.");
            loadingbar.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(CustomerLoginRegisterActivity.this,"Customer Register Successful!",Toast.LENGTH_LONG).show();
                        loadingbar.dismiss();
                    }
                    else
                    {
                        Toast.makeText(CustomerLoginRegisterActivity.this, "Registration failled! Please try again.", Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }
                }
            });
        }

    }
}
