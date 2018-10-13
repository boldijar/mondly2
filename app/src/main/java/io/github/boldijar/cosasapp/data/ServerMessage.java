package io.github.boldijar.cosasapp.data;

import com.google.gson.annotations.SerializedName;

/**
 * @author Paul
 * @since 2018.10.13
 */
public class ServerMessage {

    @SerializedName("type")
    public MessageType mType;
    @SerializedName("score")
    public int mScore;
    @SerializedName("game")
    public Game mGame;
    @SerializedName("player")
    public User mUser;
    @SerializedName("room_id")
    public int mRoomId;


    @Override
    public String toString() {
        return "ServerMessage{" +
                "mType=" + mType +
                ", mPlayerId=" + mUser +
                ", mScore=" + mScore +
                ", mGame=" + mGame +
                '}';
    }
}
