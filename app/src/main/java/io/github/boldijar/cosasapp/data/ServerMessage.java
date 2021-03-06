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
    @SerializedName("question_answered_count")
    public int mQuestionAnsweredCount;
    @SerializedName("challenged_by_name")
    public String mChallengedBy;
    @SerializedName("who_was_challenged")
    public int mWhoWasChallanged;
    @SerializedName("message")
    public String mMessage;
    @SerializedName("power_up")
    public int mPowerUp;


    @Override
    public String toString() {
        return "ServerMessage{" +
                "mType=" + mType +
                ", mScore=" + mScore +
                ", mGame=" + mGame +
                ", mUser=" + mUser +
                ", mRoomId=" + mRoomId +
                ", mQuestionAnsweredCount=" + mQuestionAnsweredCount +
                ", mChallengedBy='" + mChallengedBy + '\'' +
                ", mWhoWasChallanged=" + mWhoWasChallanged +
                ", mMessage='" + mMessage + '\'' +
                ", mPowerUp=" + mPowerUp +
                '}';
    }
}
