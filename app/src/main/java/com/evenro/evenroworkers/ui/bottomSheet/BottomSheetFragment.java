package com.evenro.evenroworkers.ui.bottomSheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.evenro.evenroworkers.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CalendarView calendar = getView().findViewById(R.id.calender_niew);
        TextView date = getActivity().findViewById(R.id.add_event_date);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                String name = "";
                if (month == 0) {
                    name = "January";
                } else if (month == 1) {
                    name = "February";
                } else if (month == 2) {
                    name = "March";
                } else if (month == 3) {
                    name = "April";
                } else if (month == 4) {
                    name = "May";
                } else if (month == 5) {
                    name = "June";
                } else if (month == 6) {
                    name = "July";
                } else if (month == 7) {
                    name = "August";
                } else if (month == 8) {
                    name = "September";
                } else if (month == 9) {
                    name = "October";
                } else if (month == 10) {
                    name = "November";
                } else if (month == 11) {
                    name = "December";
                }

                date.setText(String.valueOf(dayOfMonth) + " " + String.valueOf(name) + " " + String.valueOf(year));

            }
        });


    }

}