package com.smartbaby.care;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.smartbaby.care.Model.Video;

public class VideoActivity extends AppCompatActivity {
    private RecyclerView list_video;
    private DatabaseReference mDatabase;
    private Query mQueryCurrent;
    private FirebaseRecyclerAdapter<Video, VideoActivity.EntryViewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("VIDEO");
        mDatabase.keepSynced(true);

        list_video = findViewById(R.id.videoRecyclerView);
        list_video.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list_video.setLayoutManager(layoutManager);
    }
    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Video> options = new FirebaseRecyclerOptions.Builder<Video>()
                .setQuery(mDatabase, Video.class)
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Video, VideoActivity.EntryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull VideoActivity.EntryViewHolder entryViewHolder, int i, @NonNull Video video) {

                entryViewHolder.setVideo(video.getUrlVideo());

            }

            @NonNull
            @Override
            public VideoActivity.EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_card, parent, false);
                return new VideoActivity.EntryViewHolder(view);
            }
        };

        list_video.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    public class EntryViewHolder extends RecyclerView.ViewHolder {
        View mView;
        WebView videoView;

        public EntryViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setVideo (String url){
            videoView = (WebView) mView.findViewById(R.id.videoWebView);
            videoView.setWebChromeClient(new WebChromeClient());
            WebSettings webSettings = videoView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            videoView.loadData(url, "text/html", "utf-8");
        }

    }
}