package com.example.android.animation_assignment4;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    Spinner roleSpinner;
    DateBaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mydb=new DateBaseHelper(this);

        final EditText editUsername = (EditText) findViewById(R.id.editUserName);
        final EditText editPassword = (EditText) findViewById(R.id.editPassword);
        final EditText editPasswordAgain = (EditText) findViewById(R.id.editPasswordAgain);

        final Button btnSignup = (Button) super.findViewById(R.id.btnSignup);

        roleSpinner = (Spinner) findViewById(R.id.role_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.role_array, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!editPassword.getText().toString().trim().equals(editPasswordAgain.getText().toString().trim())) {
                    Toast.makeText(SignupActivity.this, "Repeated password should be same.", Toast.LENGTH_LONG).show();
                    return;
                }
                String uername = editUsername.getText().toString().trim();
               String pwd=editPassword.getText().toString().trim();
                boolean result=false;

                if (roleSpinner.getSelectedItem().toString().equalsIgnoreCase("Parent")){
                    result=mydb.insertDataP(uername,pwd);
                }
                else result=mydb.insertDataC(uername,pwd);

                if (result){
                    AlertDialog.Builder diag=new AlertDialog.Builder(SignupActivity.this).setTitle("Success!");
                    diag.setMessage("New account is created! Go To Log In Page");
                    diag.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i=new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(i);
                        }
                    });
                    diag.show();
                }
                else{
                    AlertDialog.Builder diag2=new AlertDialog.Builder(SignupActivity.this).setTitle("Fail!");
                    diag2.setMessage("Database Issue. Please try again");
                    diag2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    diag2.show();
                }

            }
        });

    }

}
