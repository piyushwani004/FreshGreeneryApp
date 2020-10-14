package com.piyush004.projectfirst.owner.messmenu;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.piyush004.projectfirst.R;

public class MyListAdapter extends RecyclerView.ViewHolder {

    TextView textViewName, textViewQuant, textViewPrice;

    public MyListAdapter(@NonNull View itemView) {
        super(itemView);

        textViewName = itemView.findViewById(R.id.textViewNameMess);
        textViewQuant = itemView.findViewById(R.id.textViewQuantMess);
        textViewPrice = itemView.findViewById(R.id.textViewPriceMess);
    }
}
