package com.evenro.evenroworkers.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evenro.evenroworkers.R;
import com.evenro.evenroworkers.databinding.FragmentHomeBinding;
import com.evenro.evenroworkers.ui.adapter.EventAdapter;
import com.evenro.evenroworkers.ui.allEvent.AllEventActivity;
import com.evenro.evenroworkers.ui.model.Event;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class HomeFragment extends Fragment {
    private ArrayList<Event> fullEventList;
    private RecyclerView recyclerView;
    private Map<String, Object> data;
    private FragmentHomeBinding binding;
    private Event details;
    private PieChart pieChart;
    private FirebaseFirestore db;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        pieChart = view.findViewById(R.id.pieChart);
        db = FirebaseFirestore.getInstance();

        TextView user_email_home = view.findViewById(R.id.user_email_home);

        TextView icon = view.findViewById(R.id.icon_images_homes);
        String email = user.getEmail();
        char firstCharUpper = Character.toUpperCase(email.charAt(0));
        icon.setText(String.valueOf(firstCharUpper));
        user_email_home.setText(user.getEmail());

        loadDataFromFirebase();


        fullEventList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.event_load_recycler_view);

        ImageView search_button = view.findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), AllEventActivity.class);
                startActivity(intent);
            }
        });

        TextView view_all_button = view.findViewById(R.id.view_all_button);
        view_all_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), AllEventActivity.class);
                startActivity(intent);
            }
        });

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("event").orderBy("event_date", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    fullEventList.clear();
                    for (DocumentSnapshot document : task.getResult()) {

                        String eventID = document.getId();
                        data = document.getData();
                        String eventName = (String) data.get("event_name");
                        String eventDate = (String) data.get("event_date");
                        String eventTime = (String) data.get("event_time");
                        String eventPrice = (String) data.get("price");
                        String eventOrganizerName = (String) data.get("organizer_name");
                        String eventLocation = (String) data.get("event_location");
                        String eventQty = (String) data.get("qty");
                        String eventImage = (String) data.get("event_image");
                        String eventCategory = (String) data.get("event_category");


                        Log.i("EVENT CODE TEST", eventID);
                        details = new Event(eventID, eventName, eventOrganizerName, eventDate, eventTime, eventPrice, eventQty, eventLocation, eventImage, eventCategory);
                        fullEventList.add(details);
                    }
                    updateRecyclerView(fullEventList);

                }
            }
        });

        return view;
    }

    private void loadDataFromFirebase() {
        CollectionReference eventsRef = db.collection("event");
        CollectionReference invoicesRef = db.collection("invoice");
        CollectionReference usersRef = db.collection("users");

        eventsRef.get().addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                int eventCount = task1.getResult().size();

                invoicesRef.get().addOnCompleteListener(task2 -> {
                    if (task2.isSuccessful()) {
                        int invoiceCount = task2.getResult().size();
                        Log.i("test count", String.valueOf(invoiceCount));
                        usersRef.get().addOnCompleteListener(task3 -> {
                            if (task3.isSuccessful()) {
                                int userCount = task3.getResult().size();
                                setPieChart(eventCount, invoiceCount, userCount);
                            }
                        });
                    }
                });
            }
        });
    }

    private void setPieChart(int events, int invoices, int users) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(events, "Events"));
        entries.add(new PieEntry(invoices, "Invoices"));
        entries.add(new PieEntry(users, "Users"));

        PieDataSet dataSet = new PieDataSet(entries, "Statistics");

        ArrayList<Integer> color = new ArrayList<>();
        color.add(getContext().getColor(R.color.color_item1));
        color.add(getContext().getColor(R.color.color_item2));
        color.add(getContext().getColor(R.color.color_item3));
        dataSet.setColors(color);
        dataSet.setValueTextSize(15f);

        PieData pieData = new PieData(dataSet);
        pieData.setDataSet(dataSet);
        pieChart.setData(pieData);
        new Description().setEnabled(false);
        pieChart.setCenterText("Evenro");
        pieChart.setCenterTextSize(18);
        pieChart.animateY(1000, Easing.EaseInCirc);
        pieChart.invalidate();
    }

    private void updateRecyclerView(ArrayList<Event> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new EventAdapter(list));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}