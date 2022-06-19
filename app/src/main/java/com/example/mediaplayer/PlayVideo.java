package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.halilibo.bvpkotlin.BetterVideoPlayer;

public class PlayVideo extends AppCompatActivity {

    BetterVideoPlayer videoPlayer;
    String Filepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_play_video);

        videoPlayer=findViewById(R.id.player);
        Filepath=getIntent().getStringExtra("Video");
        Uri videouri= Uri.parse(Filepath);
        videoPlayer.setSource(videouri);

    }

    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.pause();
    }
}