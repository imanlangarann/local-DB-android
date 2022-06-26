package com.iman.databaseproj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String phoneNumber;
    private String name;
    private Toolbar main_TB;

    private TextView TBuserName;
    private TextView TBphoneNumber;
    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;
    private ConstraintLayout btn_shop;

    private TextView shopNumMain;
    private TextView shopNumHelp;
    private ImageView shopIconRoot;
    private int shopNumHeight;

    int d = 50;
    int degree = 25;

    private List<Integer> orderIDs;
    private DbHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new DbHelper();

        phoneNumber = getIntent().getStringExtra("phoneNumber");
        name = getIntent().getStringExtra("userName");
        orderIDs = new ArrayList<>();



        main_TB = findViewById(R.id.main_TB);
        setSupportActionBar(main_TB);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TBuserName = main_TB.findViewById(R.id.main_userName);
        TBphoneNumber = main_TB.findViewById(R.id.main_phoneNumber);
        shopNumMain = main_TB.findViewById(R.id.shop_ic_numberOfOrdersMain);
        shopNumHelp = main_TB.findViewById(R.id.shop_ic_numberOfOrdersHelp);
        shopIconRoot = main_TB.findViewById(R.id.tvshopic);
        btn_shop = main_TB.findViewById(R.id.btn_shop);


        TBuserName.setText(name);
        TBphoneNumber.setText(phoneNumber);

        btn_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,OrdersActivity.class);
                i.putExtra("OrderIDs",convListToInt(orderIDs));
                i.putExtra("phoneNumber",phoneNumber);
                startActivity(i);
            }
        });


        List<Service> list = new ArrayList<>();

        Cursor c = helper.Select(DbHelper.Names.Services.NAME_TABLE_SERVICES,new String[]{DbHelper.Names.Services.ID, DbHelper.Names.Services.TITLE,DbHelper.Names.Services.WAGE},
                                 null,null,null,null,null,null);

        while (c.moveToNext()){
            list.add(new Service(c.getInt(c.getColumnIndex(DbHelper.Names.Services.ID)), c.getString(c.getColumnIndex(DbHelper.Names.Services.TITLE)), c.getDouble(c.getColumnIndex(DbHelper.Names.Services.WAGE))));
        }
        c.close();

        recyclerView = findViewById(R.id.mainPageRecyclerView);
        mainAdapter = new MainAdapter(list,this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mainAdapter);


        shopNumMain.post(new Runnable() {
            @Override
            public void run() {
                shopNumHeight = shopNumMain.getHeight();
            }
        });

        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(MainActivity.this, v);
                menu.getMenu().add("Personal Info").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent i = new Intent(MainActivity.this,PersonalInfo.class);
                        i.putExtra("phoneNumber",phoneNumber);
                        startActivity(i);
                        return false;
                    }
                });

                menu.show();

            }
        });

//        tmp();
    }




    public void changeShopIcon(boolean upDown,int id){


        int n = Integer.parseInt(shopNumMain.getText().toString());
        if (upDown) {
            orderIDs.add(id);
            shopNumHelp.setText(String.valueOf(n));
            n++;
            shopNumMain.setText(String.valueOf(n));

            ObjectAnimator.ofObject(shopNumMain,"translationY",new FloatEvaluator(), (shopNumHeight), 0).setDuration(3*d).start();
            ObjectAnimator.ofObject(shopNumHelp,"translationY",new FloatEvaluator(), -1*(shopNumHeight), -2*(shopNumHeight)).setDuration(3*d).start();

        }
        else {
            orderIDs.remove(orderIDs.indexOf(id));
            shopNumHelp.setText(String.valueOf(n));
            n--;
            shopNumMain.setText(String.valueOf(n));

            ObjectAnimator.ofObject(shopNumMain,"translationY",new FloatEvaluator(), -1*(shopNumHeight), 0).setDuration(3*d).start();
            ObjectAnimator.ofObject(shopNumHelp,"translationY",new FloatEvaluator(), -1*(shopNumHeight), 0).setDuration(3*d).start();
        }







        ObjectAnimator.ofObject(shopIconRoot,"rotation",new FloatEvaluator(), 0, degree).setDuration(d).start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator.ofObject(shopIconRoot,"rotation",new FloatEvaluator(), degree, -1*(degree)).setDuration(2*d).start();
            }
        },d);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator.ofObject(shopIconRoot,"rotation",new FloatEvaluator(), -1*(degree), degree).setDuration(2*d).start();
            }
        },3*d);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator.ofObject(shopIconRoot,"rotation",new FloatEvaluator(), degree, 0).setDuration(d).start();
            }
        },5*d);

    }

    private int[] convListToInt(List<Integer> list){
        int[] res = new int[list.size()];
        for (int i = 0 ; i < list.size() ; i++){
            res[i] = list.get(i);
        }

        return res;
    }



    void tmp(){
        orderIDs.add(1);
        orderIDs.add(3);
        orderIDs.add(4);
        orderIDs.add(5);
        btn_shop.callOnClick();
    }

/*    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        menu.add("personal information").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent i = new Intent(getBaseContext(),PersonalInfo.class);
                i.putExtra("phoneNumber",phoneNumber);
                startActivity(i);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }*/

}