package com.piyush004.freshgreenery.Admin.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialog;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.piyush004.freshgreenery.R;
import com.piyush004.freshgreenery.Utilities.AdminHome.AdminHolder;
import com.piyush004.freshgreenery.Utilities.AdminHome.AdminModel;

import java.util.Calendar;


public class ReportFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private View view;
    private MaterialButton materialButton;
    private RecyclerView recyclerView;
    private FirebaseRecyclerOptions<AdminModel> options;
    private FirebaseRecyclerAdapter<AdminModel, AdminHolder> adapter;
    private TextView textViewTotalRate;


    private String month;
    int yearSelected;
    int monthSelected;
    double TotalRate = 0.0;

    public ReportFragment() {
        // Required empty public constructor
    }


    public static ReportFragment newInstance(String param1, String param2) {
        ReportFragment fragment = new ReportFragment();
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

        view = inflater.inflate(R.layout.fragment_report, container, false);

        materialButton = view.findViewById(R.id.ButtonYearMonthPicker);
        textViewTotalRate = view.findViewById(R.id.textViewTotalRate);

        recyclerView = view.findViewById(R.id.AdminReportRecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Calendar calendar = Calendar.getInstance();
        yearSelected = calendar.get(Calendar.YEAR);
        monthSelected = calendar.get(Calendar.MONTH);
        final MonthYearPickerDialogFragment dialogFragment = MonthYearPickerDialogFragment.getInstance(monthSelected, yearSelected);

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogFragment.show(getChildFragmentManager(), null);

            }
        });

        dialogFragment.setOnDateSetListener(new MonthYearPickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(int year, int monthOfYear) {

                switch (monthOfYear) {
                    case 0:
                        month = "Jan";
                        break;
                    case 1:
                        month = "Feb";
                        break;
                    case 2:
                        month = "Mar";
                        break;
                    case 3:
                        month = "Apr";
                        break;
                    case 4:
                        month = "May";
                        break;
                    case 5:
                        month = "Jun";
                        break;
                    case 6:
                        month = "Jul";
                        break;
                    case 7:
                        month = "Aug";
                        break;
                    case 8:
                        month = "Sep";
                        break;
                    case 9:
                        month = "Oct";
                        break;
                    case 10:
                        month = "Nov";
                        break;
                    case 11:
                        month = "Dec";
                        break;
                }
                materialButton.setText(month + " / " + year);

                final DatabaseReference fetchAdminReport = FirebaseDatabase.getInstance().getReference().child("AdminData").child("Report").child(String.valueOf(year)).child(month);
                options = new FirebaseRecyclerOptions.Builder<AdminModel>().setQuery(fetchAdminReport, new SnapshotParser<AdminModel>() {

                    @NonNull
                    @Override
                    public AdminModel parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new AdminModel(

                                snapshot.child("OrderId").getValue(String.class),
                                snapshot.child("OrderName").getValue(String.class),
                                snapshot.child("OrderDate").getValue(String.class),
                                snapshot.child("OrderNoItems").getValue(String.class),
                                snapshot.child("OrderRate").getValue(String.class)
                        );

                    }
                }).build();
                adapter = new FirebaseRecyclerAdapter<AdminModel, AdminHolder>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull AdminHolder holder, int position, @NonNull final AdminModel model) {

                        TotalRate = TotalRate + Double.parseDouble(model.getReportOrderRate());
                        Log.e("Total", "Total : " + TotalRate);

                        holder.reportOrderId.setText(model.getReportOrderId());
                        holder.reportOrderName.setText(model.getReportOrderName());
                        holder.ReportOrderDate.setText(model.getReportOrderDate());
                        holder.reportOrderItems.setText(model.getReportOrderItems());
                        holder.ReportOrderRate.setText(model.getReportOrderRate() + "Rs");

                        textViewTotalRate.setText("" + TotalRate + "");

                    }

                    @NonNull
                    @Override
                    public AdminHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_card, parent, false);

                        return new AdminHolder(view);
                    }
                };


                adapter.startListening();
                recyclerView.setAdapter(adapter);

            }
        });

        return view;
    }
}