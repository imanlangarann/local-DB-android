package com.iman.databaseproj;

import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private List<Order> dataSet;
    private OrdersActivity ordersActivity;
    int width;
    int d = 300;

    private int position;


    public OrdersAdapter(List<Order> dataSet, OrdersActivity ordersActivity) {
        this.dataSet = dataSet;
        this.ordersActivity = ordersActivity;
        this.position = dataSet.size()+1;
    }

    @NonNull
    @Override
    public OrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_recyclerview_layout,parent,false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final OrdersAdapter.ViewHolder holder, int position) {
        holder.order = dataSet.get(position);
        holder.TV_title.setText(holder.order.getTitle());
        holder.wage = (holder.order.getCost()/(holder.order.getHours()*holder.order.getNumberOfCleaners()));
        holder.TV_cost.setText(String.format("Cost : %.2f",holder.order.getCost()));
        holder.TV_hours.setText(String.valueOf(holder.order.getHours()));
        holder.TV_numbers.setText(String.valueOf(holder.order.getNumberOfCleaners()));


        holder.itemView.post(new Runnable() {
            @Override
            public void run() {
                width = holder.itemView.getWidth();
                holder.redView.setTranslationX(width);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView TV_title;
        private TextView TV_cost;
        private TextView TV_hours;
        private TextView TV_numbers;
        private float wage;
        private ImageView BTN_delete;
        private ImageView BTN_up_hs;
        private ImageView BTN_down_hs;
        private ImageView BTN_up_ns;
        private ImageView BTN_down_ns;
        private View redView;
        private Order order;

//        private View root ;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

//            root = itemView.findViewById(R.id.rootOrdersRecyclerView);
            TV_title = itemView.findViewById(R.id.ordersRecyclerViewTitle);
            TV_cost = itemView.findViewById(R.id.ordersRecyclerViewCost);
            TV_hours = itemView.findViewById(R.id.ordersRecyclerViewHours);
            TV_numbers = itemView.findViewById(R.id.ordersRecyclerViewNumbers);

            BTN_delete = itemView.findViewById(R.id.ordersRecyclerViewDelete);
            BTN_up_hs = itemView.findViewById(R.id.btn_up_hours);
            BTN_down_hs = itemView.findViewById(R.id.btn_down_hours);
            BTN_up_ns = itemView.findViewById(R.id.btn_up_numbers);
            BTN_down_ns = itemView.findViewById(R.id.btn_down_numbers);

            redView = itemView.findViewById(R.id.red_view);



            BTN_up_hs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int n = order.getHours();
                    n++;
                    order.setHours(n);
                    TV_hours.setText(String.valueOf(n));

                    float c = wage * n * order.getNumberOfCleaners();
                    order.setCost(c);
                    TV_cost.setText(String.format("Cost : %.2f",c));
                }
            });
            BTN_down_hs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int n = order.getHours();
                    if(n>1) {
                        n--;
                        order.setHours(n);
                        TV_hours.setText(String.valueOf(n));

                        float c = wage * n * order.getNumberOfCleaners();
                        order.setCost(c);
                        TV_cost.setText(String.format("Cost : %.2f",c));
                    }
                }
            });

            BTN_up_ns.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int n = order.getNumberOfCleaners();
                    n++;
                    order.setNumberOfCleaners(n);
                    TV_numbers.setText(String.valueOf(n));

                    float c = wage * n * order.getHours();
                    order.setCost(c);
                    TV_cost.setText(String.format("Cost : %.2f",c));
                }
            });
            BTN_down_ns.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int n = order.getNumberOfCleaners();
                    if(n>1) {
                        n--;
                        order.setNumberOfCleaners(n);
                        TV_numbers.setText(String.valueOf(n));

                        float c = wage * n * order.getHours();
                        order.setCost(c);
                        TV_cost.setText(String.format("Cost : %.2f",c));
                    }
                }
            });

            BTN_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    redView.setVisibility(View.VISIBLE);
                    ObjectAnimator.ofObject(redView,"translationX",new FloatEvaluator(), width, 0).setDuration(d).start();


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ordersActivity.removeItem(order);
                            redView.setVisibility(View.GONE);
                        }
                    },d);
                }
            });

        }
    }
}
