package com.iman.databaseproj;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<Service> dataSet;
    private MainActivity mainActivity;

    public MainAdapter(List<Service> services, MainActivity mainActivity){
        this.dataSet = services;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recyclerview_layout,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.TV_title.setText(dataSet.get(position).getTitle());
        holder.TV_wage.setText(((int)(dataSet.get(position).getWage()*1000)) + " per hour");
        holder.id = dataSet.get(position).getId();

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView TV_title;
        private TextView TV_wage;
        private int id;
        private ImageView BTN_add;
        private View greenView;

        private boolean selected = false;
        int d = 300;
        int width;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            TV_title = itemView.findViewById(R.id.mainRecyclerViewTitle);
            TV_wage = itemView.findViewById(R.id.mainRecyclerViewWage);
            BTN_add = itemView.findViewById(R.id.customRecyclerViewAdd);
            greenView = itemView.findViewById(R.id.green_view);



            itemView.post(new Runnable() {
                @Override
                public void run() {
                    width = itemView.getWidth();
                    greenView.setTranslationX(width);
                }
            });


            BTN_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!selected){
                        selected = true;
                        BTN_add.animate().rotation(45).setDuration(d).start();
                        ObjectAnimator.ofObject(BTN_add,"colorFilter",new ArgbEvaluator(),Color.BLACK, Color.rgb(255,11,11)).setDuration(d).start();
                        greenView.animate().translationX(0).setDuration(d).start();
                        mainActivity.changeShopIcon(true,id);
                    }
                    else{
                        selected = false;
                        BTN_add.animate().rotation(0).setDuration(d).start();
                        ObjectAnimator.ofObject(BTN_add,"colorFilter",new ArgbEvaluator(),Color.rgb(255,11,11), Color.BLACK).setDuration(d).start();
                        greenView.animate().translationX(width).setDuration(d).start();
                        mainActivity.changeShopIcon(false,id);

                    }

                }
            });

        }
    }
}
