package com.iman.databaseproj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity {

    private DbHelper helper;

    private Button btn_loginLOGIN;
    private Button btn_signupLOGIN;
    private ConstraintLayout grayLayer;
    private TextInputEditText inputPhoneNumberLOGIN;

    private TextInputEditText inputFirstNameSIGNUP;
    private TextInputEditText inputLastNameSIGNUP;
    private TextInputEditText inputPhoneNumberSIGNUP;
    private TextInputEditText inputEmailSIGNUP;


    private Button btn_loginSIGNUP;
    private Button btn_signupSIGNUP;

    private ConstraintLayout layoutLogin;
    private ConstraintLayout layoutSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        helper = new DbHelper();
        btn_loginLOGIN = findViewById(R.id.BTN_loginLOGIN);
        btn_signupLOGIN = findViewById(R.id.BTN_signupLOGIN);
        btn_loginSIGNUP = findViewById(R.id.BTN_loginSIGNUP);
        btn_signupSIGNUP = findViewById(R.id.BTN_signupSIGNUP);
        grayLayer = findViewById(R.id.grayLayer);
        inputPhoneNumberLOGIN = findViewById(R.id.input_phoneNumberLOGIN);
        inputFirstNameSIGNUP = findViewById(R.id.input_firstNameSIGNUP);
        inputLastNameSIGNUP = findViewById(R.id.input_lastNameSIGNUP);
        inputPhoneNumberSIGNUP = findViewById(R.id.input_phoneNumberSIGNUP);
        inputEmailSIGNUP = findViewById(R.id.input_emailSIGNUP);

        layoutLogin = findViewById(R.id.layoutLogin);
        layoutSignup = findViewById(R.id.layoutSignup);

        layoutSignup.setVisibility(View.GONE);
        layoutLogin.setVisibility(View.VISIBLE);





        btn_loginLOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                final Cursor cursor = helper.Select(DbHelper.Names.Clients.NAME_TABLE_CLIENTS, new String[] {DbHelper.Names.Clients.PHONENUMBER,DbHelper.Names.Clients.NAME},
                        DbHelper.Names.Clients.PHONENUMBER + " = ?",new String[]{inputPhoneNumberLOGIN.getText().toString()},null,null,null,null);


                grayLayer.setVisibility(View.VISIBLE);
                grayLayer.setAlpha(0.0f);
                grayLayer.animate().alpha(1.0f).setDuration(500).start();

                cursor.moveToFirst();
                int cnt = cursor.getCount();
                if (cnt == 0){
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    grayLayer.setVisibility(View.GONE);

                                    inputPhoneNumberLOGIN.setError("Wrong Phone Number!");
                                    cursor.close();
                                }
                            });


                        }
                    },500);
                }
                else if (cnt == 1){
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {

                            Intent i = new Intent(v.getContext(),MainActivity.class);
                            i.putExtra("phoneNumber",cursor.getString(cursor.getColumnIndex(DbHelper.Names.Clients.PHONENUMBER)));
                            i.putExtra("userName",cursor.getString(cursor.getColumnIndex(DbHelper.Names.Clients.NAME)));
                            startActivity(i);
                            cursor.close();
                            finish();

                        }
                    },500);


                }
                else {
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {


                            Toast.makeText(LoginActivity.this, "There is something WRONG!", Toast.LENGTH_SHORT).show();
                            cursor.close();
                            finish();


                        }
                    },500);

                }

//                String msg = "";
//                msg += cursor.getCount();
//                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_LONG).show();

                /*
                grayLayer.setVisibility(View.VISIBLE);
                grayLayer.setAlpha(0.0f);
                grayLayer.animate().alpha(1.0f).setDuration(500).start();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent i = new Intent(v.getContext(),MainActivity.class);
                        i.putExtra("phoneNumber",inputPhoneNumber.getText().toString());
                        i.putExtra("userName","iman");
                        startActivity(i);
                        finish();
                    }
                },500);*/
            }
        });


        btn_signupLOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long duration = 500;
                layoutSignup.setVisibility(View.VISIBLE);
                layoutSignup.setAlpha(0.0F);
                layoutSignup.animate().alpha(1.0F).setDuration(duration).start();
                layoutLogin.animate().alpha(0.0F).setDuration(duration).start();
//                new Timer().schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                    }
//                },duration);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layoutLogin.setVisibility(View.GONE);

                    }
                },duration);
            }
        });


        btn_loginSIGNUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long duration = 500;
                layoutLogin.setVisibility(View.VISIBLE);
                layoutLogin.setAlpha(0.0F);
                layoutLogin.animate().alpha(1.0F).setDuration(duration).start();
                layoutSignup.animate().alpha(0.0F).setDuration(duration).start();
//                new Timer().schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        layoutSignup.setVisibility(View.GONE);
//                    }
//                },duration);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layoutSignup.setVisibility(View.GONE);

                    }
                },duration);
            }
        });


        btn_signupSIGNUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (inputFirstNameSIGNUP.getText().toString().trim().equals("") ){
                    inputFirstNameSIGNUP.setError("Enter your Name!");
                }
                else if(inputPhoneNumberSIGNUP.getText().toString().trim().equals("")){
                    inputPhoneNumberSIGNUP.setError("Enter your Phone Number");
                }
                else {

                    Cursor cursor = helper.Select(DbHelper.Names.Clients.NAME_TABLE_CLIENTS, new String[]{DbHelper.Names.Clients.PHONENUMBER, DbHelper.Names.Clients.NAME},
                            DbHelper.Names.Clients.PHONENUMBER + " = ?", new String[]{inputPhoneNumberSIGNUP.getText().toString()}, null, null, null, null);

                    if (cursor.getCount() == 0) {

                        ContentValues cv = new ContentValues();
                        cv.put(DbHelper.Names.Clients.ID,helper.getID(DbHelper.Names.Clients.NAME_TABLE_CLIENTS));
                        cv.put(DbHelper.Names.Clients.NAME, (inputFirstNameSIGNUP.getText().toString().trim() + " " + inputLastNameSIGNUP.getText().toString().trim()).trim());
                        cv.put(DbHelper.Names.Clients.PHONENUMBER, (inputPhoneNumberSIGNUP.getText().toString()));
                        cv.put(DbHelper.Names.Clients.EMAILADDRESS,(inputEmailSIGNUP.getText().toString().trim()));
                        cv.put(DbHelper.Names.Clients.ADDRESS,"null");

                        long i = helper.insert(DbHelper.Names.Clients.NAME_TABLE_CLIENTS,cv);

                        long duration = 700;
                        if(i == -1){

                            grayLayer.setVisibility(View.VISIBLE);
                            grayLayer.setAlpha(0.0f);
                            grayLayer.animate().alpha(1.0f).setDuration(500).start();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    grayLayer.setVisibility(View.GONE);
                                    Toast.makeText(LoginActivity.this, "There is something wrong!", Toast.LENGTH_SHORT).show();


                                }
                            },duration);

                        }
                        else {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    grayLayer.setVisibility(View.GONE);
                                    Toast.makeText(LoginActivity.this, "Signed up successfully", Toast.LENGTH_SHORT).show();

                                    inputPhoneNumberLOGIN.setText(inputPhoneNumberSIGNUP.getText().toString());
                                    btn_loginLOGIN.callOnClick();

                                }
                            },duration);
                        }

                    } else {
                        inputPhoneNumberSIGNUP.setError("Phone Number already exists!");
                    }

                    cursor.close();
                }
            }
        });


//        tmp();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.closeBD();
    }

    private void tmp() {

        final Cursor cursor = helper.Select(DbHelper.Names.Clients.NAME_TABLE_CLIENTS, new String[] {DbHelper.Names.Clients.PHONENUMBER,DbHelper.Names.Clients.NAME},
                DbHelper.Names.Clients.PHONENUMBER + " = ?",new String[]{inputPhoneNumberLOGIN.getText().toString()},null,null,null,null);

        cursor.moveToFirst();
        Intent i = new Intent(this,MainActivity.class);
        i.putExtra("phoneNumber",cursor.getString(cursor.getColumnIndex(DbHelper.Names.Clients.PHONENUMBER)));
        i.putExtra("userName",cursor.getString(cursor.getColumnIndex(DbHelper.Names.Clients.NAME)));
        startActivity(i);
        cursor.close();
        finish();

    }
}