package com.example.movies.domain.java;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
 import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Movie implements Parcelable {

    @PrimaryKey
    @NonNull
    public String tittle;
    public double rate;
    public int year;
    public String director;
    @Ignore
    public List<String> stars;
    public String summary;
    public String poster;
    public String genere;

    public Movie() {
        tittle = "";
    }

    protected Movie(Parcel in) {
        tittle = in.readString();
        rate = in.readDouble();
        year = in.readInt();
        director = in.readString();
        stars = in.createStringArrayList();
        summary = in.readString();
        poster = in.readString();
        genere = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tittle);
        dest.writeDouble(rate);
        dest.writeInt(year);
        dest.writeString(director);
        dest.writeStringList(stars);
        dest.writeString(summary);
        dest.writeString(poster);
        dest.writeString(genere);
    }
}