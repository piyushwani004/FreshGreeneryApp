package com.piyush004.freshgreenery.Utilities.AdminHome;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.piyush004.freshgreenery.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Holder extends RecyclerView.ViewHolder {


    //Home Page Holder Components
    public TextView textViewDate, textViewName, textViewPrice, textViewQuanty;
    public CircleImageView circleImageViewHome;

    public Holder(@NonNull View itemView) {
        super(itemView);

        this.textViewName = itemView.findViewById(R.id.Title_card);
        this.textViewPrice = itemView.findViewById(R.id.Price_card);
        this.textViewDate = itemView.findViewById(R.id.Date_card);
        this.textViewQuanty = itemView.findViewById(R.id.Quantity_card);
        this.circleImageViewHome = itemView.findViewById(R.id.imageViewHome);
    }

    public void setTxtName(String string) {
        textViewName.setText(string);
    }

    public void setTxtPrice(String string) {
        textViewPrice.setText(string);
    }

    public void setTxtDate(String string) {
        textViewDate.setText(string);
    }

    public void setTxtQuantity(String string) {
        textViewQuanty.setText(" " + string);
    }

    public void setImgURL(String string) {
        Picasso.get().load(string).resize(500, 500).centerCrop().rotate(0).into(circleImageViewHome);
    }


}
