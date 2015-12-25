package com.yuan.twittersearchdemo.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuan on 15/12/25.
 */
public class SearchEntity implements Parcelable {

    public List<Status> statuses;

    public Search_Metadata search_metadata;

    public SearchEntity() {
    }

    public SearchEntity(JSONObject json) throws JSONException{
        if(json.has("statuses")){
            ArrayList<Status> statuses = new ArrayList<>();
            JSONArray array = json.getJSONArray("statuses");
            for (int i = 0 ,len = array.length();i < len;i++){
                statuses.add(new Status(array.getJSONObject(i)));
            }
            this.statuses = statuses;
        }
        if(json.has("search_metadata")){
            this.search_metadata = new Search_Metadata(json.getJSONObject("search_metadata"));
        }

    }

    protected SearchEntity(Parcel in) {
        statuses = in.createTypedArrayList(Status.CREATOR);
        search_metadata = in.readParcelable(Search_Metadata.class.getClassLoader());
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
        dest.writeTypedList(statuses);
        dest.writeParcelable(search_metadata, flags);
    }

    @Override
    public String toString() {
        return "SearchEntity{" +
                "statuses=" + statuses +
                ", metadata=" + search_metadata +
                '}';
    }

    public static class Search_Metadata implements Parcelable {
        public long max_id;

        public String max_id_str;

        public String next_results;

        public long since_id;

        public String since_id_str;

        public Search_Metadata() {
        }

        public Search_Metadata(JSONObject json) throws JSONException{
            if(json.has("max_id")){
                this.max_id = json.getLong("max_id");
            }
            if(json.has("since_id")){
                this.since_id = json.getLong("since_id");
            }
            if(json.has("max_id_str")){
                this.max_id_str = json.getString("max_id_str");
            }
            if(json.has("next_results")){
                this.next_results = json.getString("next_results");
            }
            if(json.has("since_id_str")){
                this.since_id_str = json.getString("since_id_str");
            }
        }

        protected Search_Metadata(Parcel in) {
            max_id = in.readLong();
            max_id_str = in.readString();
            next_results = in.readString();
            since_id = in.readLong();
            since_id_str = in.readString();
        }

        public static final Creator<Search_Metadata> CREATOR = new Creator<Search_Metadata>() {
            @Override
            public Search_Metadata createFromParcel(Parcel in) {
                return new Search_Metadata(in);
            }

            @Override
            public Search_Metadata[] newArray(int size) {
                return new Search_Metadata[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(max_id);
            dest.writeString(max_id_str);
            dest.writeString(next_results);
            dest.writeLong(since_id);
            dest.writeString(since_id_str);
        }

        @Override
        public String toString() {
            return "Search_Metadata{" +
                    "max_id=" + max_id +
                    ", max_id_str='" + max_id_str + '\'' +
                    ", next_results='" + next_results + '\'' +
                    ", since_id=" + since_id +
                    ", since_id_str=" + since_id_str +
                    '}';
        }
    }
}
