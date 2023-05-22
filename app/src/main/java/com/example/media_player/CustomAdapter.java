package com.example.media_player;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Song_Model> {


    LayoutInflater inflater;
    public static class Holder {
        TextView name;
        TextView artist;
    }


    public CustomAdapter(@NonNull Context context,@NonNull ArrayList<Song_Model> objects) {
        super(context,R.layout.recycler_view_layout,objects);
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Holder holder = new Holder();
        Song_Model song = getItem(position);
        if(convertView==null) {
            convertView = inflater.inflate(R.layout.recycler_view_layout, parent, false);

            holder.name = convertView.findViewById(R.id.display_song);
            holder.artist=convertView.findViewById(R.id.display_artist);
            convertView.setTag(holder);
        }
        else
        {
            holder=(Holder) convertView.getTag();
        }

        holder.artist.setText(song.getArtist());
        holder.name.setText(song.getName());
        return  convertView;
    }
}
