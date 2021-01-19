package com.smartbaby.care;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.smartbaby.care.Model.Artikel;
import com.squareup.picasso.Picasso;

public class ArtikelActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    private Query mQueryCurrent;
    private FirebaseRecyclerAdapter<Artikel, ArtikelActivity.EntryViewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artikel);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("ARTIKEL");
        mDatabase.keepSynced(true);

        recyclerView = findViewById(R.id.artikelRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        return;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Artikel> options = new FirebaseRecyclerOptions.Builder<Artikel>()
                .setQuery(mDatabase, Artikel.class)
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Artikel, ArtikelActivity.EntryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ArtikelActivity.EntryViewHolder entryViewHolder, int i, @NonNull Artikel artikel) {

                entryViewHolder.setTitle(artikel.getJudul());
                entryViewHolder.setGambar(artikel.getGambarUrl());

                entryViewHolder.setIsi(artikel.getIsi());
                entryViewHolder.setImg(artikel.getGambarUrl());

            }

            @NonNull
            @Override
            public ArtikelActivity.EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artikel_card, parent, false);
                return new ArtikelActivity.EntryViewHolder(view);
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    public class EntryViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView e_judul;
        ImageView e_gambar;
        String e_isi, e_img;

        public EntryViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String judul = String.valueOf(e_judul.getText());
                    artikelPage(judul, e_img, e_isi);
                }
            });
        }

        public void setGambar (String imgUrl){
            e_gambar = (ImageView) mView.findViewById(R.id.gambarArtikel);
            Picasso.with(ArtikelActivity.this).load(imgUrl).into(e_gambar);
        }

        public void setTitle(String title){
            e_judul = (TextView) mView.findViewById(R.id.judulArtikel);
            e_judul.setText(title);
        }

        public void setIsi (String isiArtikel){
            e_isi = isiArtikel;
        }

        public void setImg (String img){
            e_img = img;
        }

    }

    private void artikelPage(String judul, String e_img, String e_isi) {
        Intent i = new Intent(ArtikelActivity.this, Artikel_Page.class);
        i.putExtra("iJudul", judul);
        i.putExtra("iIsi", e_isi);
        i.putExtra("iImg", e_img);
        startActivity(i);
    }
}