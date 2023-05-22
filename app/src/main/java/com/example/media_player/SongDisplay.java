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

public class SongDisplay extends Fragment {


    ListView listView;
    TextView Song,NoSong;
    ArrayList<Song_Model> songList;
    CustomAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_song_display, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Song=view.findViewById(R.id.Song_header);
        NoSong=view.findViewById(R.id.Empty_song);
        listView =view.findViewById(R.id.list_view);
        songList= new ArrayList<>();
        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ARTIST,
        };

        Cursor cursor= (getContext()).getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,null,null,"TITLE ASC");
        while(cursor.moveToNext())
        {
            Song_Model song=new Song_Model(cursor.getString(0), cursor.getString(1), cursor.getString(2) );
            song.setArtist(cursor.getString(3));
            if(new File(song.getPath()).exists())
                songList.add(song);
        }

        if(songList.size()==0)
        {
            NoSong.setVisibility(View.VISIBLE);
        }
        else
        {
            adapter=new CustomAdapter(getContext(),songList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((adapterView, view1, i, l) -> {
                Bundle bundle=new Bundle();
                Song_Model song= adapter.getItem(i);
                bundle.putSerializable("song",song);
                bundle.putParcelableArrayList("songlist",songList);

                AudioPlayer audioPlayer=new AudioPlayer();
                audioPlayer.setArguments(bundle);

                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout,audioPlayer);
                fragmentTransaction.commit();
            });
        }



        super.onViewCreated(view, savedInstanceState);
        cursor.close();

    }

}