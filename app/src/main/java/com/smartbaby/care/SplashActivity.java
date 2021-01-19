package com.smartbaby.care;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final TextView textSplash = findViewById(R.id.txtSplash);
        FirebaseDatabase Datab=FirebaseDatabase.getInstance();
        DatabaseReference ref=Datab.getReference("SPLASH/judul");
        DatabaseReference ref2=Datab.getReference("SPLASH/gambar");
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String h = dataSnapshot.getValue(String.class);
                ImageView imageView = findViewById(R.id.imgSplash);
                Picasso.with(SplashActivity.this)
                        .load(h)
                        .into(imageView);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ERROR", databaseError.getMessage());
            }
        });

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String h = dataSnapshot.getValue(String.class);
                textSplash.setText(h);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ERROR", databaseError.getMessage());
            }
        });
        Button btnmulai = findViewById(R.id.mulai);
        btnmulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashActivity.this,Home_Page.class);
                startActivity(intent);
            }
        });
    }
}