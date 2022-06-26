package com.iman.databaseproj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {


    private DbHelper helper;

    private RecyclerView recyclerView;
    private OrdersAdapter ordersAdapter;

    private List<Order> orderList;

    private int clientID;
    private String clientAddress;


    private NestedScrollView layout_orders;
    private NestedScrollView layout_address;
    private TextView toolbarTitle;

    private EditText inputAddress;
    private TextView dateYear;
    private TextView dateMonth;
    private TextView dateDay;


    private static final int ORDERS_LAYOUT = 1;
    private static final int ADDRESS_LAYOUT = 2;

    private int current_layout = ORDERS_LAYOUT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        helper = new DbHelper();

        layout_orders = findViewById(R.id.order_lay_orders);
        layout_address = findViewById(R.id.order_lay_address);
        toolbarTitle = findViewById(R.id.order_toolbarTitle);
        inputAddress = findViewById(R.id.input_address);
        dateYear = findViewById(R.id.date_year);
        dateMonth = findViewById(R.id.date_month);
        dateDay = findViewById(R.id.date_day);

        int[] orderIDs = getIntent().getIntArrayExtra("OrderIDs");

        Cursor c0 = helper.Select(DbHelper.Names.Clients.NAME_TABLE_CLIENTS, new String[] {DbHelper.Names.Clients.ID, DbHelper.Names.Clients.ADDRESS},
                DbHelper.Names.Clients.PHONENUMBER + " = ?",new String[]{getIntent().getStringExtra("phoneNumber")},null,null,null,null);

        c0.moveToFirst();
        clientID = c0.getInt(c0.getColumnIndex(DbHelper.Names.Clients.ID));
        clientAddress = c0.getString(c0.getColumnIndex(DbHelper.Names.Clients.ADDRESS));
        inputAddress.setText(clientAddress);
        c0.close();


        orderList = new ArrayList<>();

        for(int id : orderIDs){
            Cursor c = helper.Select(DbHelper.Names.Services.NAME_TABLE_SERVICES,new String[]{DbHelper.Names.Services.ID,DbHelper.Names.Services.TITLE,DbHelper.Names.Services.WAGE},
                    DbHelper.Names.Services.ID + " = ?",new String[]{String.valueOf(id)},null,null,null,null);
            c.moveToFirst();
            orderList.add(new Order(clientID,c.getInt(c.getColumnIndex(DbHelper.Names.Services.ID)),1,1,(float) c.getDouble(c.getColumnIndex(DbHelper.Names.Services.WAGE)),"null",c.getString(c.getColumnIndex(DbHelper.Names.Services.TITLE))));
            c.close();
        }

        ordersAdapter = new OrdersAdapter(orderList,this);
        recyclerView = findViewById(R.id.ordersPageRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(ordersAdapter);

        findViewById(R.id.btn_up_year).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateYear.setText(String.valueOf((Integer.parseInt(dateYear.getText().toString()))+1));
            }
        });

        findViewById(R.id.btn_up_month).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(dateMonth.getText().toString())+1;
                if(n <= 12){
                    dateMonth.setText(String.valueOf(n));
                }
                else{
                    dateMonth.setText("1");
                    findViewById(R.id.btn_up_year).callOnClick();
                }
            }
        });

        findViewById(R.id.btn_up_day).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(dateDay.getText().toString())+1;
                if(n <= 31){
                    dateDay.setText(String.valueOf(n));
                }
                else{
                    dateDay.setText("1");
                    findViewById(R.id.btn_up_month).callOnClick();
                }
            }
        });


        findViewById(R.id.btn_down_year).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateYear.setText(String.valueOf((Integer.parseInt(dateYear.getText().toString()))-1));
            }
        });

        findViewById(R.id.btn_down_month).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(dateMonth.getText().toString())-1;
                if(n >= 1){
                    dateMonth.setText(String.valueOf(n));
                }
                else{
                    dateMonth.setText("12");
                    findViewById(R.id.btn_down_year).callOnClick();
                }
            }
        });

        findViewById(R.id.btn_down_day).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(dateDay.getText().toString())-1;
                if(n >= 1){
                    dateDay.setText(String.valueOf(n));
                }
                else{
                    dateDay.setText("31");
                    findViewById(R.id.btn_down_month).callOnClick();
                }
            }
        });

    }

    public void removeItem(Order order){
        orderList.remove(order);
        ordersAdapter.notifyDataSetChanged();
    }

    public void btnClicked(View v){
        ImageView i = findViewById(R.id.next_ordersActivity);
        if (v.getId() == R.id.back_ordersActivity) {
            if (current_layout == ORDERS_LAYOUT) {
                finish();
            } else {
                current_layout = ORDERS_LAYOUT;
                layout_orders.setVisibility(View.VISIBLE);
                layout_address.setVisibility(View.GONE);
                toolbarTitle.setText("Orders");
                i.setImageDrawable(getResources().getDrawable(R.drawable.ic_back,getApplicationContext().getTheme()));
                i.setRotation(180);
            }
        }
        else if(v.getId() == R.id.next_ordersActivity){
            if(current_layout == ORDERS_LAYOUT){
                current_layout = ADDRESS_LAYOUT;
                i.setImageDrawable(getResources().getDrawable(R.drawable.ic_check,getApplicationContext().getTheme()));
                i.setRotation(0);
                layout_orders.setVisibility(View.GONE);
                layout_address.setVisibility(View.VISIBLE);
                toolbarTitle.setText("Address Confirmation");
            }
            else{
                if (!clientAddress.equals(inputAddress.getText().toString().trim())){
                    clientAddress = inputAddress.getText().toString().trim();
                    ContentValues cv = new ContentValues();
                    cv.put(DbHelper.Names.Clients.ADDRESS, clientAddress);
                    helper.update(DbHelper.Names.Clients.NAME_TABLE_CLIENTS,cv, DbHelper.Names.Clients.ID + " = ?", new String[]{String.valueOf(clientID)});
                    Toast.makeText(this, "new address saved", Toast.LENGTH_SHORT).show();
                }
                insertNewOrder();
                Toast.makeText(this, "Order confirmed successfully", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }


    private void insertNewOrder(){
        StringBuilder date = new StringBuilder();
        date.append(dateYear.getText().toString());
        date.append(".");
        String m = dateMonth.getText().toString();
        if (m.length()<2) m = "0"+m;
        date.append(m);
        date.append(".");
        String d = dateDay.getText().toString();
        if (d.length()<2) d = "0"+d;
        date.append(d);

        ContentValues cv;
        for(Order o : orderList){
            o.setOrderDate(date.toString());
            cv = new ContentValues();
            cv.put(DbHelper.Names.Orders.ID,helper.getID(DbHelper.Names.Orders.NAME_TABLE_SERVICES));
            cv.put(DbHelper.Names.Orders.CLIENTID,o.getClientID());
            cv.put(DbHelper.Names.Orders.SERVICEID,o.getServiceID());
            cv.put(DbHelper.Names.Orders.HOURS,o.getHours());
            cv.put(DbHelper.Names.Orders.NUMBEROFCLEANERS,o.getNumberOfCleaners());
            cv.put(DbHelper.Names.Orders.COST,o.getCost());
            cv.put(DbHelper.Names.Orders.ORDERDATE,o.getOrderDate());
            cv.put(DbHelper.Names.Orders.ISCHECKED,"FALSE");
            helper.insert(DbHelper.Names.Orders.NAME_TABLE_SERVICES,cv);
        }

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.closeBD();
    }
}