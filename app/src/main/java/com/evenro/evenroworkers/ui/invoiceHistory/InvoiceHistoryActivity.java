package com.evenro.evenroworkers.ui.invoiceHistory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evenro.evenroworkers.EmptyActivity;
import com.evenro.evenroworkers.InvoiceEmptyActivity;
import com.evenro.evenroworkers.R;
import com.evenro.evenroworkers.ui.adapter.InvoiceAdapter;
import com.evenro.evenroworkers.ui.model.InvoiceData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class InvoiceHistoryActivity extends AppCompatActivity {
    private ArrayList<InvoiceData> fullEventList;
    private RecyclerView recyclerView;
    private Map<String, Object> data;
    private InvoiceData invoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_invoice_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(fullEventList !=null){
            fullEventList = new ArrayList<>();
            recyclerView = findViewById(R.id.invoice_history_recycler_view);
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            firestore.collection("invoice").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        fullEventList.clear();
                        for (DocumentSnapshot document : task.getResult()) {
                            String eventID = document.getId();
                            data = document.getData();
                            String eventName = (String) data.get("event_name");
                            String eventDate = (String) data.get("payment_date");
                            String eventTime = (String) data.get("buyer_name");
                            String ticketPrice = (String) data.get("ticket_price");
                            String eventQty = (String) data.get("qty");
                            String eventImage = (String) data.get("images");


                            Log.i("EVENT CODE TEST", eventID);
                            invoice = new InvoiceData(eventID, eventName, eventDate, eventTime, ticketPrice, eventQty, eventImage);
                            fullEventList.add(invoice);
                        }
                        updateRecycler(fullEventList);

                    }
                }


            });
        }else{
            Intent intent = new Intent(InvoiceHistoryActivity.this, InvoiceEmptyActivity.class);
            startActivity(intent);
        }


    }

    private void updateRecycler(ArrayList<InvoiceData> list) {
        if (list.isEmpty()) {

        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(new InvoiceAdapter(list));
        }
    }

}