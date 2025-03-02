package com.evenro.evenroworkers.ui.allEvent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evenro.evenroworkers.EmptyActivity;
import com.evenro.evenroworkers.R;
import com.evenro.evenroworkers.ui.adapter.ExpireEventAdapter;
import com.evenro.evenroworkers.ui.adapter.InvoiceAdapter;
import com.evenro.evenroworkers.ui.model.ExpireEvent;
import com.evenro.evenroworkers.ui.model.InvoiceData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class ExpireActivity extends AppCompatActivity {

    private ArrayList<ExpireEvent> fullEventList;
    private RecyclerView recyclerView;
    private Map<String, Object> data;
    private ExpireEvent expireEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_expire);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
        String date = simpleDateFormat.format(new Date());
        Date newDate;
        try {
            newDate = simpleDateFormat.parse(date);
            Log.d("data snapshot", simpleDateFormat.format(newDate));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        fullEventList = new ArrayList<>();
        recyclerView = findViewById(R.id.expire_event_recycler_view);


        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("event").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                fullEventList.clear();
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                    String eventDateString = snapshot.getString("event_date");
                    if(eventDateString != null){
                        try {
                            Date eventDate = simpleDateFormat.parse(eventDateString);
                            if (eventDate.before(newDate)) {
                                if(snapshot.getId() !=null){
                                    String eventID = snapshot.getId();
                                    data = snapshot.getData();
                                    String eventName = (String) data.get("event_name");
                                    String eventOrganizer  = (String) data.get("organizer_name");
                                    String eventDates = (String) data.get("event_date");
                                    String eventQty = (String) data.get("qty");
                                    String eventImage = (String) data.get("event_image");

                                    expireEvent = new ExpireEvent(eventID,eventName,eventOrganizer,eventDates,eventQty,eventImage);

                                    fullEventList.add(expireEvent);
                                    Log.w("data snapshot", snapshot.getData().toString());
                                }else{
                                    Intent intent = new Intent(ExpireActivity.this, EmptyActivity.class);
                                    startActivity(intent);
                                }
                            }
                        } catch (ParseException e) {
                            Log.e("DateParsing", "Error parsing event date for document " + snapshot.getId() + ": " + e.getMessage());
                        }
                    }

                }
                updateRecycler(fullEventList);
            }
        });


    }

    private void updateRecycler(ArrayList<ExpireEvent> list) {

        if(list.isEmpty()){
            Intent intent = new Intent(ExpireActivity.this,EmptyActivity.class);
            startActivity(intent);
        }else{
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(new ExpireEventAdapter(list));
        }
    }
}