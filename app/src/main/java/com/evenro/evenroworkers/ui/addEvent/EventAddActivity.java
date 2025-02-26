package com.evenro.evenroworkers.ui.addEvent;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cloudinary.android.MediaManager;
import com.evenro.evenroworkers.R;
import com.evenro.evenroworkers.ui.bottomSheet.BottomSheetFragment;
import com.evenro.evenroworkers.ui.bottomSheet.TimeBottomSheetFragment;
import com.evenro.evenroworkers.ui.model.AirplaneModeBroadcastReceiver;
import com.evenro.evenroworkers.ui.model.CloudinaryHelper;
import com.evenro.evenroworkers.ui.model.Location;
import com.evenro.evenroworkers.ui.model.SpinnerItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class EventAddActivity extends AppCompatActivity {

    TextView category, location;
    private FirebaseFirestore firebaseFirestore;

    private EditText event_add_mobile_number, event_name, event_time, event_date, event_price, event_qty, event_description,add_organizer_name;
    private Spinner spinner, location_spinner;
    String categ;
    String loca;
    private ImageView imageView;
    private Uri imageUri;
    private ArrayList<Location> locations = new ArrayList<>();
    Map<String, Object> data;

    private AirplaneModeBroadcastReceiver broadcastReceiver;

    private Location selectedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event_add);
        Map config = new HashMap();
        config.put("cloud_name", "dzqpctth7");

        try {
            MediaManager.get();
        } catch (IllegalStateException e) {
            MediaManager.init(getApplicationContext(), config);
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add_view), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        ImageButton date_image_button = findViewById(R.id.date_add_bottom_sheet_button);
        date_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        });

        ImageButton time_image_button = findViewById(R.id.time_bottom_sheet_button);
        time_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeBottomSheetFragment timeBottomSheetFragment = new TimeBottomSheetFragment();
                timeBottomSheetFragment.show(getSupportFragmentManager(), timeBottomSheetFragment.getTag());
            }
        });

        ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(),
                        new ActivityResultCallback<Uri>() {
                            @Override
                            public void onActivityResult(Uri uri) {
                                if (uri != null) {
                                    imageUri = uri;
                                    imageView.setImageURI(uri);
                                }
                            }
                        });

        imageView = findViewById(R.id.event_img_view);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());


            }
        });
        location_spinner = findViewById(R.id.location_spinner);
        locations.add(new Location("Select Location", "0"));
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("locations").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    locations.clear();

                    for (DocumentSnapshot document : task.getResult()) {
                        loadLocations(document);
                    }

                }
            }
        });

        LocationAdapter locationAdapter = new LocationAdapter(getApplicationContext(), R.layout.custome_spinner_location_item, locations);
        location_spinner.setAdapter(locationAdapter);


        location_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLocation = (Location) parent.getItemAtPosition(position);

                loca = selectedLocation.getName();
                Log.i("TEST CODE", loca);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spinner = findViewById(R.id.event_category_item);

        ArrayList<SpinnerItem> spinnerItems = new ArrayList<>();
        spinnerItems.add(new SpinnerItem(R.drawable.arrow_drop_down, "Select"));
        spinnerItems.add(new SpinnerItem(R.drawable.music, "Music"));
        spinnerItems.add(new SpinnerItem(R.drawable.painting, "Art"));
        spinnerItems.add(new SpinnerItem(R.drawable.tennis_ball, "Sport"));

        SpinnerAdapter arrayAdapter = new SpinnerAdapter(getApplicationContext(), R.layout.custome_spinner_item, spinnerItems);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = view.findViewById(R.id.spinner_items_text);
                categ = (String) category.getText();
                Log.i("TEST CODE", categ);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button add_event_button = findViewById(R.id.event_add_button);
        event_name = findViewById(R.id.add_event_name);
        event_time = findViewById(R.id.add_event_time);
        event_date = findViewById(R.id.add_event_date);
        event_price = findViewById(R.id.add_event_price);
        event_qty = findViewById(R.id.add_event_qty);
        event_description = findViewById(R.id.add_event_description);
        event_add_mobile_number = findViewById(R.id.add_event_mobile_number);
        add_organizer_name = findViewById(R.id.add_organizer_name);


        add_event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (event_name.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter a Event Name", Toast.LENGTH_SHORT).show();
                } else if (event_add_mobile_number.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter your Mobile Number", Toast.LENGTH_SHORT).show();
                    Log.i("EVENT ADD", "Type a Mobile Number");
                } else if (add_organizer_name.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter your Organizer Name", Toast.LENGTH_SHORT).show();
                    Log.i("EVENT ADD", "Type a Mobile Number");
                }  else if (event_time.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Choose a Event Time", Toast.LENGTH_SHORT).show();
                    Log.i("EVENT ADD", "Choose your Time");
                } else if (event_date.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Choose a Event Date", Toast.LENGTH_SHORT).show();
                    Log.i("EVENT ADD", "Choose your Date");
                } else if (event_price.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Type Your Ticket Price", Toast.LENGTH_SHORT).show();
                    Log.i("EVENT ADD", "Type a Ticket Price");
                } else if (event_qty.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Type Your Ticket Quantity", Toast.LENGTH_SHORT).show();
                    Log.i("EVENT ADD", "Type a Ticket Quantity");
                } else if (spinner.getSelectedItem() == null) {
                    Toast.makeText(getApplicationContext(), "Select an Event Category", Toast.LENGTH_SHORT).show();
                    Log.i("EVENT ADD", "Select an Event Category");
                } else if (location_spinner.getSelectedItem() == null) {
                    Toast.makeText(getApplicationContext(), "Select an Event Location", Toast.LENGTH_SHORT).show();
                    Log.i("EVENT ADD", "Select an Event Category");
                } else if (event_description.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Type an Event Description", Toast.LENGTH_SHORT).show();
                    Log.i("EVENT ADD", "Type an Event Description");
                } else {

                    String eventName = event_name.getText().toString().trim();
                    String eventTime = event_time.getText().toString().trim();
                    String eventDate = event_date.getText().toString().trim();
                    String eventPrice = event_price.getText().toString().trim();
                    String eventQty = event_qty.getText().toString().trim();
                    String eventCategory = String.valueOf(categ);
                    String eventLocation = loca;
                    String eventDescription = event_description.getText().toString().trim();
                    String eventmobileNumber = event_add_mobile_number.getText().toString().trim();
                    String oraganizerName = add_organizer_name.getText().toString().trim();


                    CloudinaryHelper.uploadImage(imageUri, null, new CloudinaryHelper.OnUploadCompleteListener() {
                        @Override
                        public void onUploadComplete(String url) {

                            firebaseFirestore = FirebaseFirestore.getInstance();
                            HashMap<String, Object> event_data = new HashMap<>();
                            event_data.put("event_name", eventName);
                            event_data.put("organizer_name", oraganizerName);
                            event_data.put("qty", eventQty);
                            event_data.put("event_date", eventDate);
                            event_data.put("event_time", eventTime);
                            event_data.put("price", eventPrice + ".00");
                            event_data.put("event_category", eventCategory);
                            event_data.put("event_location", eventLocation);
                            event_data.put("event_description", eventDescription);
                            event_data.put("mobile_number", eventmobileNumber);
                            event_data.put("event_image", url);

                            firebaseFirestore.collection("event").add(event_data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.i("EVENT ADD", documentReference.getId());
                                    Log.i("EVENT ADD", "Success Add Event");
                                    Toast.makeText(getApplicationContext(), "Success Full Add Event", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    Log.i("EVENT ADD", e.toString());
                                }
                            });

                        }
                    });


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            event_name.setText("");
                            event_date.setText("");
                            event_time.setText("");
                            add_organizer_name.setText("");
                            event_qty.setText("");
                            event_price.setText("");
                            location_spinner.setSelection(0);
                            event_description.setText("");
                            spinner.setSelection(0);
                            event_add_mobile_number.setText("");
                            imageView.setImageResource(R.drawable.add_photo_alternate);
                        }
                    });
                }
            }
        });


    }

    private void loadLocations(DocumentSnapshot document) {
        String eventID = document.getId();
        data = document.getData();
        String locationName = (String) data.get("locationName");
        String locationLatlng = (String) data.get("locationLatlng");


        Log.i("EVENT CODE TEST", eventID);

        selectedLocation = new Location(locationName, locationLatlng);
        locations.add(selectedLocation);
    }


    @Override
    protected void onStart() {
        super.onStart();
        broadcastReceiver = new AirplaneModeBroadcastReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);

    }
}

class SpinnerAdapter extends ArrayAdapter<SpinnerItem> {

    List<SpinnerItem> spinnerItems;
    int layout;

    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull List<SpinnerItem> objects) {
        super(context, resource, objects);
        spinnerItems = objects;
        layout = resource;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(layout, parent, false);
        ImageView imageView = view.findViewById(R.id.spinner_image);
        TextView textView = view.findViewById(R.id.spinner_items_text);

        SpinnerItem spinnerItem = spinnerItems.get(position);
        imageView.setImageResource(spinnerItem.getItemResourceId());
        textView.setText(spinnerItem.getName());
        return view;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getDropDownView(position, convertView, parent);
    }
}

class LocationAdapter extends ArrayAdapter<Location> {

    List<Location> spinnerItems;
    int layout;

    public LocationAdapter(@NonNull Context context, int resource, @NonNull List<Location> objects) {
        super(context, resource, objects);
        spinnerItems = objects;
        layout = resource;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(layout, parent, false);
        TextView textView = view.findViewById(R.id.spinner_items_text);
        Location spinnerItem = spinnerItems.get(position);
        textView.setText(spinnerItem.getName());
        return view;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getDropDownView(position, convertView, parent);
    }

}


