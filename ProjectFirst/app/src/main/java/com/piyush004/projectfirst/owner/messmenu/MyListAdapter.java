package com.piyush004.projectfirst.owner.messmenu;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.piyush004.projectfirst.R;

public class MyListAdapter extends RecyclerView.ViewHolder {

    TextView textViewName;
    TextView textViewQuant;
    TextView textViewPrice;

    public MyListAdapter(@NonNull View itemView) {
        super(itemView);

        textViewName = itemView.findViewById(R.id.textViewNameMess);
        textViewQuant = itemView.findViewById(R.id.textViewQuantMess);
        textViewPrice = itemView.findViewById(R.id.textViewPriceMess);
    }

    public void setTxtName(String string) {
        textViewName.setText(string);
    }


    public void setTxtQuant(String string) {
        textViewQuant.setText(string);
    }

    public void setTxtPrice(String string) {
        textViewPrice.setText(string);
    }

}
