package com.piyush004.freshgreenery.Utilities.AdminHome;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.piyush004.freshgreenery.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Holder extends RecyclerView.ViewHolder {


    //Home Page Holder Components...
    public TextView textViewDate, textViewName, textViewPrice, textViewQuanty, textViewTotalQuanty;
    public CircleImageView circleImageViewHome;

    //Cart page Holder Components...
    public ImageView imageViewMinusCart, imageViewPlusCart, imageViewCheck;
    public TextView textViewTitleCart, textViewPriceCart, textViewWeightCart, textViewUserQuantityCard, textViewfrom_end, cart_totalRate;


    public Holder(@NonNull View itemView) {
        super(itemView);

        this.textViewName = itemView.findViewById(R.id.Title_card);
        this.textViewPrice = itemView.findViewById(R.id.Price_card);
        this.textViewDate = itemView.findViewById(R.id.Date_card);
        this.textViewQuanty = itemView.findViewById(R.id.Quantity_card);
        this.textViewTotalQuanty = itemView.findViewById(R.id.TotalQuantity_card);

        this.circleImageViewHome = itemView.findViewById(R.id.imageViewHome);

        this.imageViewMinusCart = itemView.findViewById(R.id.cart_minus_img);
        this.imageViewPlusCart = itemView.findViewById(R.id.cart_plus_img);

        //cart
        this.imageViewCheck = itemView.findViewById(R.id.action_check);
        this.cart_totalRate = itemView.findViewById(R.id.cart_totalRate);
        this.textViewTitleCart = itemView.findViewById(R.id.from_name);
        this.textViewfrom_end = itemView.findViewById(R.id.from_end);
        this.textViewPriceCart = itemView.findViewById(R.id.plist_price_text);
        this.textViewWeightCart = itemView.findViewById(R.id.plist_weight_text);
        this.textViewUserQuantityCard = itemView.findViewById(R.id.cart_product_quantity);

    }

    //Home Section
    public void setTxtName(String string) {
        textViewName.setText(string);
    }

    public void setTxtPrice(String string) {

        textViewPrice.setText("Rs " + string);
    }

    public void setTxtUserRate(String price, String Quant) {

        textViewPrice.setText("Rs" + price + " / " + Quant);
    }

    public void setTxtDate(String string) {
        textViewDate.setText(string);
    }

    public void setTxtQuantity(String string) {
        textViewQuanty.setText(" Per " + string);
    }

    public void setTxtTotalQuantity(String string1, String string2) {
        textViewTotalQuanty.setText(string1 + " " + string2);
    }


    public void setImgURL(String string) {
        Picasso.get().load(string).resize(500, 500).centerCrop().rotate(0).into(circleImageViewHome);
    }


    //cart Section

    public void setTxtTotalRate(String string) {
        cart_totalRate.setText(string);
    }

    public void setTxtTitleCart(String string) {
        textViewTitleCart.setText(string);
    }

    public void setTxtRateCart(String string) {
        textViewPriceCart.setText("Rs. " + string);
    }

    public void setTxtWeightCart(String string) {
        textViewWeightCart.setText("/" + string);
    }

    public void setTxtUserQuantCart(String string) {
        textViewUserQuantityCard.setText(string);
    }

    public void setTxtFromEndQuantity(String quantity, String weight) {
        textViewfrom_end.setText(quantity + "" + weight);
    }

}
