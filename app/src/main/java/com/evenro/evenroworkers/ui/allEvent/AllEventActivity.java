package com.evenro.evenroworkers.ui.allEvent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evenro.evenroworkers.R;
import com.evenro.evenroworkers.ui.adapter.EventAdapter;
import com.evenro.evenroworkers.ui.model.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class AllEventActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Event> fullEventList;
    private ArrayList<Event> eventList;
    private EditText searchEditText;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_event);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.splash_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fullEventList = new ArrayList<>();
        eventList = new ArrayList<>();
        recyclerView = findViewById(R.id.event_recycler_view);
        searchEditText = findViewById(R.id.search_text);
        firestore = FirebaseFirestore.getInstance();

        loadAllEvents();

        ImageButton searchButton = findViewById(R.id.filter_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchEditText.getText().toString().trim();
                Log.d("SearchDebug", "Searching for: " + searchText);
                performSearch(searchText);
            }
        });
    }

    private void loadAllEvents() {
        firestore.collection("event").orderBy("event_date", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            fullEventList.clear();
                            for (DocumentSnapshot document : task.getResult()) {
                                Event event = loadEventDetailsObject(document);
                                fullEventList.add(event);
                            }
                            updateRecyclerView(fullEventList);
                            searchEditText.setText("");
                        } else {
                            Log.e("AllEventActivity", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void performSearch(String searchText) {
        if (searchText.isEmpty()) {
            updateRecyclerView(fullEventList);
        } else {
            searchEvents(searchText);
        }
    }

    private void searchEvents(String searchText) {
        String queryText = searchText.toLowerCase(Locale.getDefault());
        eventList.clear();
        for (Event event : fullEventList) {
            if (event.getEventName().toLowerCase(Locale.getDefault()).contains(queryText) ||
                    event.getEventCategory().toLowerCase(Locale.getDefault()).contains(queryText) ||
                    event.getEventOrganizerName().toLowerCase(Locale.getDefault()).contains(queryText) ||
                    event.getEventLocation().toLowerCase(Locale.getDefault()).contains(queryText)) {
                eventList.add(event);
            }
        }
        updateRecyclerView(eventList);
    }

    private void updateRecyclerView(ArrayList<Event> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new EventAdapter(list));
    }

    private Event loadEventDetailsObject(DocumentSnapshot document) {
        String eventID = document.getId();
        Map<String, Object> data = document.getData();
        String eventName = (String) data.get("event_name");
        String eventDate = (String) data.get("event_date");
        String eventTime = (String) data.get("event_time");
        String eventPrice = (String) data.get("price");
        String eventCategory = (String) data.get("event_category");
        String eventOrganizerName = (String) data.get("organizer_name");
        String eventLocation = (String) data.get("event_location");
        String eventQty = (String) data.get("qty");
        String eventImage = (String) data.get("event_image");

        Log.i("EVENT CODE TEST", eventID);

        return new Event(
                eventID,
                eventName,
                eventOrganizerName,
                eventDate,
                eventTime,
                eventPrice,
                eventQty,
                eventLocation,
                eventImage,
                eventCategory
        );
    }
}