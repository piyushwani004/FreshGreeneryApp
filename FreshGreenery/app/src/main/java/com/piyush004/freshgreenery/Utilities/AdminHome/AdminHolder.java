package com.piyush004.freshgreenery.Utilities.AdminHome;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.piyush004.freshgreenery.R;

public class AdminHolder extends RecyclerView.ViewHolder {

    public ImageView imageViewCheckOrderConfirm;
    public TextView textViewOrderId, textViewUserName, textViewRateAdmin, textViewDateAdmin ,textViewOrderMethod;
    public TextView textViewMobile, textViewAddress, textViewTime, textViewNoItems, textViewCity, textViewSociety, textViewFlatNo;
    public RecyclerView recyclerViewItems;

    public LinearLayout hiddenView;
    public MaterialCardView cardView;

    public AdminHolder(@NonNull View itemView) {
        super(itemView);

        this.imageViewCheckOrderConfirm = itemView.findViewById(R.id.imageViewCheckOrderConfirm);

        this.textViewOrderId = itemView.findViewById(R.id.textViewOrderidAdmin);
        this.textViewUserName = itemView.findViewById(R.id.textViewUserName);
        this.textViewRateAdmin = itemView.findViewById(R.id.textViewRateAdmin);
        this.textViewDateAdmin = itemView.findViewById(R.id.textViewDateAdmin);
        this.textViewOrderMethod = itemView.findViewById(R.id.textViewOrderMethod);

        this.textViewMobile = itemView.findViewById(R.id.textNotiMobileAdmin);
        this.textViewAddress = itemView.findViewById(R.id.textNotiAddressAdmin);
        this.textViewTime = itemView.findViewById(R.id.textNotiTimeAdmin);
        this.textViewNoItems = itemView.findViewById(R.id.textNotiNoItemAdmin);
        this.textViewCity = itemView.findViewById(R.id.textNotiCityAdmin);
        this.textViewSociety = itemView.findViewById(R.id.textNotiSocietyAdmin);
        this.textViewFlatNo = itemView.findViewById(R.id.textNotiFlatAdmin);

        this.recyclerViewItems = itemView.findViewById(R.id.recycleNotiAdmin);

        this.hiddenView = itemView.findViewById(R.id.AdminNotiHidden_view);
        this.cardView = itemView.findViewById(R.id.base_cardView_admin);

    }


}
