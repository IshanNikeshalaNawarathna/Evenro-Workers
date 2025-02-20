package com.evenro.evenroworkers.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evenro.evenroworkers.R;
import com.evenro.evenroworkers.databinding.FragmentHomeBinding;
import com.evenro.evenroworkers.ui.adapter.EventAdapter;
import com.evenro.evenroworkers.ui.model.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class HomeFragment extends Fragment {
    private ArrayList<Event> fullEventList;
    private RecyclerView recyclerView;
    private Map<String, Object> data;
    private FragmentHomeBinding binding;
    private Event details;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        fullEventList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.event_load_recycler_view);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("event").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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


                        Log.i("EVENT CODE TEST", eventID);
                        details = new Event(eventID,eventName,eventOrganizerName,eventDate,eventTime,eventPrice,eventQty,eventLocation,eventImage);
                        fullEventList.add(details);
                    }
                    updateRecyclerView(fullEventList);

                }
            }
        });

        return view;
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