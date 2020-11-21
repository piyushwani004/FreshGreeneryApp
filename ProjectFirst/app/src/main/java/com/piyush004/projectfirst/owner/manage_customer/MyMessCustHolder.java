package com.piyush004.projectfirst.owner.manage_customer;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.piyush004.projectfirst.R;
import com.squareup.picasso.Picasso;

public class MyMessCustHolder extends RecyclerView.ViewHolder {

    TextView textViewTitle, textViewAddress, textViewMobile, textViewCity;
    ImageView imageView;


    public MyMessCustHolder(@NonNull View itemView) {
        super(itemView);

        this.textViewTitle = itemView.findViewById(R.id.title_card_o);
        this.textViewAddress = itemView.findViewById(R.id.address_card_o);
        this.textViewMobile = itemView.findViewById(R.id.mobile_card_o);
        this.textViewCity = itemView.findViewById(R.id.join_date_card_o);
        this.imageView = itemView.findViewById(R.id.imageViewMess_o);
    }

    public void setTxtTitle(String string) {
        textViewTitle.setText(string);
    }


    public void setTxtAddress(String string) {
        textViewAddress.setText(string);
    }

    public void setTxtMobile(String string) {
        textViewMobile.setText(string);
    }

    public void setTxtCity(String string) {
        textViewCity.setText(string);
    }

    public void setTxtImg(String string) {
        Picasso.get().load(string).into(imageView);
    }

}
