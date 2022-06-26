package com.iman.databaseproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class PersonalInfo extends AppCompatActivity {

    private DbHelper helper;

    private String phoneNumber;


    private Button btn_save;
    private TextInputEditText input_name;
    private TextInputEditText input_email;
    private TextInputEditText input_address;

    private String name;
    private String email;
    private String address;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Personal Information");
        helper = new DbHelper();


        btn_save = findViewById(R.id.BTN_savePERSONAL);
        input_name = findViewById(R.id.input_NamePERSONAL);
        input_email = findViewById(R.id.input_emailPERSONAL);
        input_address = findViewById(R.id.input_addressPERSONAL);


        phoneNumber = getIntent().getStringExtra("phoneNumber");

        setValues();

        setListeners();

    }

    public void setValues(){
        Cursor c = helper.Select(DbHelper.Names.Clients.NAME_TABLE_CLIENTS,new String[]{DbHelper.Names.Clients.NAME, DbHelper.Names.Clients.EMAILADDRESS, DbHelper.Names.Clients.ADDRESS},
                DbHelper.Names.Clients.PHONENUMBER+ " = ?", new String[]{phoneNumber},null,null,null,null);
        c.moveToFirst();
        name = c.getString(c.getColumnIndex(DbHelper.Names.Clients.NAME));
        email = c.getString(c.getColumnIndex(DbHelper.Names.Clients.EMAILADDRESS));
        address = c.getString(c.getColumnIndex(DbHelper.Names.Clients.ADDRESS));
        c.close();

        input_name.setText(name);
        input_email.setText(email);
        input_address.setText(address);
    }

    public void setListeners(){
        TextWatcher tv = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(input_name.getText().toString().trim().equals(name) &&
                    input_email.getText().toString().trim().equals(email) &&
                    input_address.getText().toString().trim().equals(address) &&
                    btn_save.isEnabled())
                    btn_save.setEnabled(false);
                else if (!btn_save.isEnabled())
                    btn_save.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };


        input_name.addTextChangedListener(tv);
        input_email.addTextChangedListener(tv);
        input_address.addTextChangedListener(tv);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();

                if (!input_name.getText().toString().trim().equals(name)){
                    name = input_name.getText().toString().trim();
                    cv.put(DbHelper.Names.Clients.NAME,name);
                }

                if (!input_email.getText().toString().trim().equals(email)){
                    email = input_email.getText().toString().trim();
                    cv.put(DbHelper.Names.Clients.EMAILADDRESS,email);
                }

                if (!input_address.getText().toString().trim().equals(address)){
                    address = input_address.getText().toString().trim();
                    cv.put(DbHelper.Names.Clients.ADDRESS,address);
                }

                helper.update(DbHelper.Names.Clients.NAME_TABLE_CLIENTS,cv, DbHelper.Names.Clients.PHONENUMBER+" = ?",new String[]{phoneNumber});
                v.setEnabled(false);
                Toast.makeText(PersonalInfo.this, "information updated!", Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.closeBD();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}