package com.example.media_player;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class VideoPlayer extends Fragment {


    private VideoView videoView;
    private Song_Model video;

    private void setVideo(String path)
    {
        Uri uri=Uri.parse(path);
        videoView.setVideoURI(uri);
        videoView.start();

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (getArguments() != null) {
            video = (Song_Model) getArguments().getSerializable("video");
        }
        return inflater.inflate(R.layout.fragment_video_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        videoView=view.findViewById(R.id.video_view);
        videoView.setMediaController(new MediaController(getContext()));
        setVideo(video.getPath());

    }
}