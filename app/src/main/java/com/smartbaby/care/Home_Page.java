package com.smartbaby.care;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home_Page extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_layout);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        final Home_Page v = this;
        FirebaseUser current = mAuth.getCurrentUser();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        int frag = 0;

        Intent intent = getIntent();
        if(intent.hasExtra("FRAGMENT")){
            frag = intent.getIntExtra("FRAGMENT", 0);
        }

        Frag(frag);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        if(current != null ){
            toolbar.inflateMenu(R.menu.login);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(final MenuItem menuItem) {

                    switch (menuItem.getItemId()){
                        case R.id.item3:
                            String admin = user.getEmail().toString();
                            mDatabase.child("ADMIN").orderByChild("email").equalTo(admin).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()){
                                        Intent i = new Intent(Home_Page.this, Halaman_Admin.class);
                                        startActivity(i);
                                        finish();

                                    }else {
                                        Toast.makeText(getApplicationContext(), "Anda Bukan Admin!", Toast.LENGTH_SHORT).show();

                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            break;
                        case R.id.item2:
                            Intent i2 = new Intent(Home_Page.this, AkunSetting.class);
                            startActivity(i2);

                            break;
                        case R.id.item1:
                            logout();
                            break;
                        case R.id.edukasi:
                            Frag(0);
                            break;
                        case R.id.story:
                            Frag(2);
                            break;
                        case R.id.forum:
                            Intent i = new Intent(Home_Page.this, ForumActivity.class);
                            startActivity(i);
                            break;


                    }
                    return false;
                }
            });
        }else{
            toolbar.inflateMenu(R.menu.toolbar_menu);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    Intent login = new Intent(Home_Page.this, Login_Page.class);
                    switch (menuItem.getItemId()){
                        case R.id.edukasi:
                            Frag(0);
                            break;
                        case R.id.story:
                            Frag(2);
                            break;
                        case R.id.forum:
                            Intent i = new Intent(Home_Page.this, ForumActivity.class);
                            startActivity(i);
                            break;
                        case R.id.login:
                            Intent in = new Intent(Home_Page.this, Login_Page.class);
                            startActivity(in);
                            break;
                    }
                    return false;
                }
            });
        }

    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(Home_Page.this, Home_Page.class);
        startActivity(i);
        finish();
    }

    private void Frag(int frag){
        tab1 homeFragment = new tab1();
        ForumFragment forumFragment = new ForumFragment();
        tab2 videoFragment = new tab2();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (frag){
            case 0:
                fragmentTransaction.replace(R.id.fragmen,  homeFragment);

                break;
            case 1:
                fragmentTransaction.replace(R.id.fragmen,  forumFragment);

                break;
            case 2:
                fragmentTransaction.replace(R.id.fragmen,  videoFragment);
                break;
            default:
                fragmentTransaction.replace(R.id.fragmen,  homeFragment);
                break;
        }

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //finish();
                        finishAffinity();
//                        MyActivity.this.onSuperBackPressed();
                        //super.onBackPressed();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    /*
    if (handleCancel()){
        super.onBackPressed();
    }
        */
    }


}
