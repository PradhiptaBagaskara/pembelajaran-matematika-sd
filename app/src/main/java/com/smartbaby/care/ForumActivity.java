package com.smartbaby.care;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ForumActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser current = mAuth.getCurrentUser();
        Button loginbtn = findViewById(R.id.loginom);

        if (current != null){
            Log.d("onForum", "onCreate: run with fragment");
            loginbtn.setVisibility(View.INVISIBLE);
            ForumFragment forumFragment = new ForumFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.tab3,  forumFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else {
            Log.d("onForum", "onCreate: run with login btn");

            loginbtn.setVisibility(View.VISIBLE);
            loginbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseUser currentUser = mAuth.getCurrentUser();

                    //user dereng login, buka login page
                    Intent login = new Intent(ForumActivity.this, Login_Page.class);
                    startActivity(login);

                }
            });
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}