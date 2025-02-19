package com.evenro.evenroworkers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.evenro.evenroworkers.ui.loging.SignInActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    public static FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.splash_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

        @Override
        protected void onStart() {
            super.onStart();

            firebaseAuth = FirebaseAuth.getInstance();
            Thread thread = new Thread() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ProgressBar bar = findViewById(R.id.progressBar);
                            bar.setVisibility(View.VISIBLE);
                        }
                    });
                    try {
                        Thread.sleep(1000);
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                            startActivity(intent);

                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            thread.start();

        }
}