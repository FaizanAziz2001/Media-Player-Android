package com.example.media_player;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.File;
import java.util.ArrayList;


public class Video_Display extends Fragment {

    ListView listView;
    TextView Video, NoVideo;
    ArrayList<Song_Model> VideoList;
    CustomAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_display, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Video =view.findViewById(R.id.Video_header);
        NoVideo =view.findViewById(R.id.Empty_video);
        listView =view.findViewById(R.id.list_view_video);
        VideoList = new ArrayList<>();
        String[] projection = {
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DURATION
        };

        Cursor cursor= (getContext()).getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,projection,null,null,null);
        while(cursor.moveToNext())
        {
            Song_Model video=new Song_Model(cursor.getString(0), cursor.getString(1), cursor.getString(2) );
            if(new File(video.getPath()).exists())
                VideoList.add(video);
        }

        if(VideoList.size()==0)
        {
            NoVideo.setVisibility(View.VISIBLE);
        }
        else
        {
            adapter=new CustomAdapter(getContext(), VideoList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((adapterView, view1, i, l) -> {
                Bundle bundle=new Bundle();
                Song_Model video= adapter.getItem(i);
                bundle.putSerializable("video",video);
                bundle.putParcelableArrayList("videolist", VideoList);

                VideoPlayer videoPlayer=new VideoPlayer();
                videoPlayer.setArguments(bundle);

                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout,videoPlayer);
                fragmentTransaction.commit();
            });
        }



        super.onViewCreated(view, savedInstanceState);
        cursor.close();

    }
}