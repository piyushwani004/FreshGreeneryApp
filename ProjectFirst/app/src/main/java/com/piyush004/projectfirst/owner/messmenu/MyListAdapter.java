package com.piyush004.projectfirst.owner.messmenu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.piyush004.projectfirst.R;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {
    private MessMenuModel[] listdata;

    // RecyclerView recyclerView;
    public MyListAdapter(MessMenuModel[] listdata) {
        this.listdata = listdata;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MessMenuModel myListData = listdata[position];
        holder.textViewName.setText(listdata[position].getMessMenuName());
        holder.textViewQuantity.setText(listdata[position].getMessMenuQuantity());
        holder.textViewPrice.setText(listdata[position].getMessMenuPrice());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "click on item: " + myListData.getMessMenuName(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName, textViewQuantity, textViewPrice;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewNameMess);
            this.textViewQuantity = (TextView) itemView.findViewById(R.id.textViewQuantMess);
            this.textViewPrice = (TextView) itemView.findViewById(R.id.textViewPriceMess);

            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayoutMessMenu);
        }
    }
}
