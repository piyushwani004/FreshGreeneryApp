package com.piyush004.projectfirst.customer.all_mess;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.piyush004.projectfirst.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    Context c;
    ArrayList<AllMessModel> model;

    public MyAdapter(Context c, ArrayList<AllMessModel> models) {
        this.c = c;
        model = models;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_mess_customer_card, null);


        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.textViewTitle.setText(model.get(position).getTitle());
        holder.textViewAddress.setText(model.get(position).getAddress());
        holder.textViewMobile.setText(model.get(position).getModile());
        holder.textViewCity.setText(model.get(position).getCity());

    }

    @Override
    public int getItemCount() {
        return model.size();
    }
}
