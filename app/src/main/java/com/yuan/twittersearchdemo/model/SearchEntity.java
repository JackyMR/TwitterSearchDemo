package com.yuan.twittersearchdemo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Yuan on 15/12/22.
 */
public class SearchEntity implements Parcelable {

    public Meta meta;

    public long id;

    public String id_str;

    public String text;

    public User user;

    public String geo;

    public long retweet_count;

    public long favorite_count;

    public boolean favorited;

    public boolean retweeted;

    public String lang;

    public SearchEntity(){}

    protected SearchEntity(Parcel in) {
        meta = in.readParcelable(Meta.class.getClassLoader());
        id = in.readLong();
        id_str = in.readString();
        text = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
        geo = in.readString();
        retweet_count = in.readLong();
        favorite_count = in.readLong();
        favorited = in.readByte() != 0;
        retweeted = in.readByte() != 0;
        lang = in.readString();
    }

    public static final Creator<SearchEntity> CREATOR = new Creator<SearchEntity>() {
        @Override
        public SearchEntity createFromParcel(Parcel in) {
            return new SearchEntity(in);
        }

        @Override
        public SearchEntity[] newArray(int size) {
            return new SearchEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(meta, flags);
        dest.writeLong(id);
        dest.writeString(id_str);
        dest.writeString(text);
        dest.writeParcelable(user, flags);
        dest.writeString(geo);
        dest.writeLong(retweet_count);
        dest.writeLong(favorite_count);
        dest.writeByte((byte) (favorited ? 1 : 0));
        dest.writeByte((byte) (retweeted ? 1 : 0));
        dest.writeString(lang);
    }


    public static class Meta implements Parcelable {

        public String iso_language_code;

        public String result_type;

        public Meta() {
        }

        protected Meta(Parcel source) {
            this.iso_language_code = source.readString();
            this.result_type = source.readString();
        }

        public static final Creator<Meta> CREATOR = new Creator<Meta>() {
            @Override
            public Meta createFromParcel(Parcel source) {
                Meta meta = new Meta(source);
                return meta;
            }

            @Override
            public Meta[] newArray(int size) {
                return new Meta[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(iso_language_code);
            dest.writeString(result_type);
        }
    }
}
