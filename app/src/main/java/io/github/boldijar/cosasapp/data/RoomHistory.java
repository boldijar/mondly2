package io.github.boldijar.cosasapp.data;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

/**
 * @author Paul
 * @since 2018.10.13
 */
public class RoomHistory {
    @SerializedName("winner")
    public boolean mWinner;
    @SerializedName("updated_at")
    public Timestamp mUpdatedAt;
    @SerializedName("created_at")
    public String mCreatedAt;
    @SerializedName("score")
    public int mScore;
    @SerializedName("user_id")
    public int mUserId;
    @SerializedName("room_id")
    public int mRoomId;
    @SerializedName("id")
    public int mId;
}
