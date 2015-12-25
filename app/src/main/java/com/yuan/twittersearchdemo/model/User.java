package com.yuan.twittersearchdemo.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Yuan on 15/12/22.
 */
public class User implements Parcelable{

    public long id;

    public String id_str;

    public String name;

    public String screen_name;

    public String location;

    public String description;

    public String url;

    public User(){}

    public User(JSONObject json) throws JSONException{
        if(json.has("id")){
            this.id = json.getLong("id");
        }
        if(json.has("id_str")){
            this.id_str = json.getString("id_str");
        }
        if(json.has("name")){
            this.name = json.getString("name");
        }
        if(json.has("screen_name")){
            this.screen_name = json.getString("screen_name");
        }
        if(json.has("location")){
            this.location = json.getString("location");
        }
        if(json.has("id_str")){
            this.description = json.getString("description");
        }
        if(json.has("id_str")){
            this.url = json.getString("url");
        }
    }

    protected User(Parcel in) {
        id = in.readLong();
        id_str = in.readString();
        name = in.readString();
        screen_name = in.readString();
        location = in.readString();
        description = in.readString();
        url = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
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
        dest.writeString(name);
        dest.writeString(screen_name);
        dest.writeString(location);
        dest.writeString(description);
        dest.writeString(url);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", id_str='" + id_str + '\'' +
                ", name='" + name + '\'' +
                ", screen_name='" + screen_name + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
