package io.github.boldijar.cosasapp.data;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Paul
 * @since 2018.10.13
 */
public class Room {

    @SerializedName("id")
    public int mId;

    @SerializedName("players")
    public List<User> mPlayers;


    @SerializedName("created_at")
    public Timestamp mCreated;
}
