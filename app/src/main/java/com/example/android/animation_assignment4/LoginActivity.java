package com.example.android.animation_assignment4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

//
// Login the user
//
public class LoginActivity extends AppCompatActivity {


    CheckBox checkUsername;
    CheckBox checkPassword;

    Button btnLogin;
    Button btnSignup;
    Spinner roleSpinner;
    EditText editUsername;
    EditText editPassword;

    DateBaseHelper mydb;

    int level;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mydb = new DateBaseHelper(this);

        editUsername = (EditText) findViewById(R.id.editUserName);
        editPassword = (EditText) findViewById(R.id.editPassword);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup = (Button) findViewById(R.id.btnSignup);

        checkUsername = (CheckBox) findViewById(R.id.check_username);
        checkPassword = (CheckBox) findViewById(R.id.check_password);

        final SharedPreferences sharedPreferences = getSharedPreferences("RememberAccount", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        String username = sharedPreferences.getString("username", "");
        String password = sharedPreferences.getString("password", "");

        editUsername.setText(username);
        editPassword.setText(password);

        Boolean isRmbUsername = sharedPreferences.getBoolean("rmbUsername", false);
        Boolean isRmbPassword = sharedPreferences.getBoolean("rmbPassword", false);

        checkUsername.setChecked(isRmbUsername);
        checkPassword.setChecked(isRmbPassword);
//        checkUsername.setSelected(isRmbUsername);
//        checkPassword.setSelected(isRmbPassword);

        roleSpinner = (Spinner) findViewById(R.id.role_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.role_array, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);
        roleSpinner.setBackgroundColor(Color.RED);

        checkUsername.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putString("username", editUsername.getText().toString().trim());
                    Log.v("CCR", "Remember Username Checked");
                } else {
                    editor.putString("username", "");
                    Log.v("CCR", "Remember Username Not Checked");
                }
                editor.commit();
            }
        });

        checkPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putString("password", editPassword.getText().toString().trim());
                } else editor.putString("password", "");
                editor.commit();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putBoolean("rmbUsername", checkUsername.isChecked());
                editor.putBoolean("rmbPassword", checkPassword.isChecked());
                editor.commit();
                boolean result = matchUserPwd();
                if (result && roleSpinner.getSelectedItem().toString().trim().equalsIgnoreCase("Kid")) {
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.putExtra("username",editUsername.getText().toString().trim());
                    startActivity(i);
                }
                else if (result && roleSpinner.getSelectedItem().toString().trim().equalsIgnoreCase("Parent")){
                    Intent i=new Intent(LoginActivity.this, ChildReport.class);
                    i.putExtra("username", editUsername.getText().toString().trim());
                    startActivity(i);
                }
            }
        });

    }

    public void signup(View view) {
        Intent i = new Intent(this, SignupActivity.class);
        startActivity(i);
    }


    public boolean matchUserPwd() {

        String username = editUsername.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        Log.v("CRR", username + "/" + password);
        Cursor cursor;
        if (roleSpinner.getSelectedItem().toString().trim().equalsIgnoreCase("Kid")) {
            cursor = mydb.getRecByUsernameC(username);
        } else {
            cursor = mydb.getRecByUsernameP(username);
        }
        if (cursor.moveToNext() == false) {
            Toast.makeText(this, "This is no such username. Please double check your input", Toast.LENGTH_LONG).show();
            cursor.close();
            return false;
        }
        if (password.equals(cursor.getString(2))) {
            if (roleSpinner.getSelectedItem().toString().trim().equalsIgnoreCase("Kid")){
                level=cursor.getInt(3);
            }
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }
}



