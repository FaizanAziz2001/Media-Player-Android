package com.example.media_player;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AudioPlayer extends Fragment {

    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private Handler handler;
    private Runnable runnable;
    private ArrayList<Song_Model> Playlist=new ArrayList<>();
    private Integer current_index=0;
    private Song_Model song;
    private TextView Time_end;
    private boolean Isplay;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            song = (Song_Model) getArguments().getSerializable("song");
        }
        Playlist=getArguments().getParcelableArrayList("songlist");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_audio_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        handler=new Handler();
        seekBar=view.findViewById(R.id.seek);
        ImageView play = view.findViewById(R.id.play);
        ImageView restart = view.findViewById(R.id.restart);
        ImageView next = view.findViewById(R.id.next);
        ImageView previous = view.findViewById(R.id.previous);
        TextView name=view.findViewById(R.id.name_TV);
        Time_end=view.findViewById(R.id.time_end);


        mediaPlayer=new MediaPlayer();
        mediaPlayer.reset();
        try {
            name.setText(song.getName());
            mediaPlayer.setDataSource(song.getPath());
            mediaPlayer.prepare();
            Time_end.setText(convertoMMS(song.getDuration()));
        } catch (IOException e) {
            e.printStackTrace();
        }


        runnable = new Runnable() {
            @Override
            public void run() {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this,500);
            }
        };

        play.setImageResource(R.drawable.pause_icon);
        Isplay=true;
        Play();

        play.setOnClickListener(view1 -> {
            if(mediaPlayer==null)
            {

                mediaPlayer=new MediaPlayer();
                mediaPlayer.reset();
                try {
                    mediaPlayer.setDataSource(song.getPath());
                    mediaPlayer.prepare();
                    Time_end.setText(convertoMMS(song.duration));
                    Isplay=true;
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Play();

            }else {
                if (Isplay) {
                    play.setImageResource(R.drawable.play_icon);
                    mediaPlayer.pause();
                    handler.removeCallbacks(runnable);
                    Isplay = false;
                } else if (!Isplay) {
                    play.setImageResource(R.drawable.pause_icon);
                    Isplay = true;
                    Play();
                }
            }


        });



        restart.setOnClickListener(view13 -> {
            if(mediaPlayer!=null)
            {
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
                play.setImageResource(R.drawable.pause_icon);
                Isplay=true;
                Play();
            }
        });

        next.setOnClickListener(view14 -> {
            if(current_index==Playlist.size()-1)
                current_index=0;
            else
                current_index++;

            if(mediaPlayer.isPlaying())
                mediaPlayer.stop();
            try {
                name.setText(Playlist.get(current_index).getName());
                mediaPlayer.reset();
                mediaPlayer.setDataSource(Playlist.get(current_index).getPath());
                Time_end.setText(convertoMMS(Playlist.get(current_index).getDuration()));
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Play();

        });

        previous.setOnClickListener(view15 -> {
            if(current_index==0)
                current_index=Playlist.size()-1;
            else
                current_index--;

            if(mediaPlayer.isPlaying())
                mediaPlayer.stop();
            try {
                mediaPlayer.reset();
                name.setText(Playlist.get(current_index).getName());
                mediaPlayer.setDataSource(Playlist.get(current_index).getPath());
                Time_end.setText(convertoMMS(Playlist.get(current_index).getDuration()));
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Play();
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b)
                    mediaPlayer.seekTo(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mediaPlayer.setOnCompletionListener(mediaPlayer -> mediaPlayer.seekTo(0));

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.reset();
    }

    @SuppressLint("DefaultLocale")
    public static String convertoMMS(String duration)
    {
        long milis=Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(milis)%TimeUnit.HOURS.toMinutes(1)
                ,TimeUnit.MILLISECONDS.toSeconds(milis)%TimeUnit.MINUTES.toSeconds(1));
    }
    private void Play()
    {
        mediaPlayer.start();
        seekBar.setMax(mediaPlayer.getDuration());
        handler.postDelayed(runnable,0);
    }


}