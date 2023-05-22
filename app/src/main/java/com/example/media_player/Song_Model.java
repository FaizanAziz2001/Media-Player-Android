package com.example.media_player;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Song_Model implements Serializable, Parcelable {
    String path;
    String name;
    String duration;
    String artist;

    Song_Model(String name,String path,String duration)
    {
        this.path=path;
        this.duration=duration;
        this.name=name;
        artist=null;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public String getPath() {
        return path;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeString(this.path);
        parcel.writeString(this.duration);
        parcel.writeString(this.artist);

    }

    public static final Parcelable.Creator<Song_Model> CREATOR
            = new Parcelable.Creator<Song_Model>() {
        public Song_Model createFromParcel(Parcel in) {
            return new Song_Model(in);
        }

        public Song_Model[] newArray(int size) {
            return new Song_Model[size];
        }
    };

    private Song_Model(Parcel in) {
        this.name = in.readString();
        this.path = in.readString();
        this.duration = in.readString();
        this.artist=in.readString();
    }
}
