package com.iman.databaseproj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity {

    private Button btn_login;
    private ConstraintLayout grayLayer;
    private ProgressBar pb_login;
    private TextInputEditText inputPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.BTN_login);
        grayLayer = findViewById(R.id.grayLayer);
        pb_login = findViewById(R.id.PB_login);
        inputPhoneNumber = findViewById(R.id.input_phoneNumber);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                grayLayer.setVisibility(View.VISIBLE);
                grayLayer.setAlpha(0.0f);
                grayLayer.animate().alpha(1.0f).setDuration(500).start();

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent i = new Intent(v.getContext(),Main2Activity.class);
                        i.putExtra("phoneNumber",inputPhoneNumber.getText().toString());
                        i.putExtra("userName","iman");
                        startActivity(i);
                        finish();
                    }
                },500);
            }
        });

    }
}