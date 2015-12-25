package com.yuan.twittersearchdemo.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Yuan on 15/12/22.
 */
public class Status implements Parcelable {

    public long id;

    public String id_str;

    public String text;

    public User user;

    public String geo;

    public long retweet_count;

    public long favorite_count;

    public Status(){}

    public Status(JSONObject json) throws JSONException {
        if(json.has("id")){
            this.id = json.getLong("id");
        }
        if(json.has("id_str")){
            this.id_str = json.getString("id_str");
        }
        if(json.has("text")){
            this.text = json.getString("text");
        }
        if(json.has("geo")){
            this.geo = json.getString("geo");
        }
        if(json.has("retweet_count")){
            this.retweet_count = json.getLong("retweet_count");
        }
        if(json.has("favorite_count")){
            this.id = json.getLong("favorite_count");
        }
        if(json.has("user")){
            this.user = new User(json.getJSONObject("user"));
        }

    }

    protected Status(Parcel in) {
        id = in.readLong();
        id_str = in.readString();
        text = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
        geo = in.readString();
        retweet_count = in.readLong();
        favorite_count = in.readLong();
    }

    public static final Creator<Status> CREATOR = new Creator<Status>() {
        @Override
        public Status createFromParcel(Parcel in) {
            return new Status(in);
        }

        @Override
        public Status[] newArray(int size) {
            return new Status[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(id_str);
        dest.writeString(text);
        dest.writeParcelable(user, flags);
        dest.writeString(geo);
        dest.writeLong(retweet_count);
        dest.writeLong(favorite_count);
    }

    @Override
    public String toString() {
        return "Status{" +
                ", id=" + id +
                ", id_str='" + id_str + '\'' +
                ", text='" + text + '\'' +
                ", user=" + user +
                ", geo='" + geo + '\'' +
                ", retweet_count=" + retweet_count +
                ", favorite_count=" + favorite_count +
                '}';
    }
}
