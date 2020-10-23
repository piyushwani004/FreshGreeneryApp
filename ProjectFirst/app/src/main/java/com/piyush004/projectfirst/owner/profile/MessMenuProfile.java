package com.piyush004.projectfirst.owner.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.piyush004.projectfirst.LoginKey;
import com.piyush004.projectfirst.R;
import com.piyush004.projectfirst.owner.messmenu.MessMenuModel;
import com.piyush004.projectfirst.owner.messmenu.MyListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessMenuProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessMenuProfile extends Fragment {
    private RecyclerView recyclerView;
    private FirebaseRecyclerOptions<MessMenuModel> options;
    private FirebaseRecyclerAdapter<MessMenuModel, MyListAdapter> adapter;
    private String login_name;
    View view;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MessMenuProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessMenuProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static MessMenuProfile newInstance(String param1, String param2) {
        MessMenuProfile fragment = new MessMenuProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mess_menu_profile, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewProfile);
        recyclerView.setHasFixedSize(true);
        login_name = LoginKey.loginKey;

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        DatabaseReference nm = FirebaseDatabase.getInstance().getReference().child("RegisterType").child(login_name).child("MessMenu");

        options = new FirebaseRecyclerOptions.Builder<MessMenuModel>().setQuery(nm, new SnapshotParser<MessMenuModel>() {
            @NonNull
            @Override
            public MessMenuModel parseSnapshot(@NonNull DataSnapshot snapshot) {
                return new MessMenuModel(
                        snapshot.child("ItemName").getValue().toString(),
                        snapshot.child("ItemQuantity").getValue().toString(),
                        snapshot.child("ItemPrice").getValue().toString()


                );
            }
        }).build();
        adapter = new FirebaseRecyclerAdapter<MessMenuModel, MyListAdapter>(options) {

            @Override
            protected void onBindViewHolder(@NonNull MyListAdapter holder, int position, @NonNull MessMenuModel model) {

                holder.setTxtName(model.getMessMenuName());
                holder.setTxtQuant(model.getMessMenuQuantity());
                holder.setTxtPrice(model.getMessMenuPrice());
            }

            @NonNull
            @Override
            public MyListAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

                return new MyListAdapter(view);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);

        return view;
    }
}