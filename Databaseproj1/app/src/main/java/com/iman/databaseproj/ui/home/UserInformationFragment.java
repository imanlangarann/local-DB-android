package com.iman.databaseproj.ui.home;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.iman.databaseproj.Main2Activity;
import com.iman.databaseproj.R;

public class UserInformationFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ImageButton btn_edit;
    private EditText input_name;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_user_information, container, false);
        btn_edit = root.findViewById(R.id.btn_edit_name);
        input_name = root.findViewById(R.id.input_name);


        input_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String tag = ((ImageButton) btn_edit).getTag().toString();

                if(tag.equals("edit")){
                    ((ImageButton) btn_edit).setImageDrawable(getActivity().getDrawable(R.drawable.ic_save));
                    ((ImageButton) btn_edit).setTag("save");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(v.getContext(), "ok "+input_name.isFocusable(), Toast.LENGTH_SHORT).show();
                input_name.setFocusableInTouchMode(true);
                Toast.makeText(v.getContext(), "ok "+input_name.isFocusable(), Toast.LENGTH_SHORT).show();*/

                if(input_name.isFocusable()){
                    input_name.setFocusable(false);
                    input_name.setFocusableInTouchMode(false);
                }
                else {
                    input_name.setFocusable(true);
                    input_name.setFocusableInTouchMode(true);
                }

                String tag = ((ImageButton) v).getTag().toString();

                if(tag.equals("save")){
                    ((ImageButton) v).setImageDrawable(getActivity().getDrawable(R.drawable.ic_edit));
                    ((ImageButton) v).setTag("edit");
                    Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG).show();
                }

                        //setImageDrawable(getActivity().getDrawable(R.drawable.ic_save));

            }
        });
        return root;
    }
}