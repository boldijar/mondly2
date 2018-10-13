package io.github.boldijar.cosasapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Paul
 * @since 2018.10.13
 */
public class Game implements Parcelable {

    @SerializedName("questions")
    public List<Question> mQuestions;
    @SerializedName("players")
    public List<User> mPlayers;

    public int mRoomId;

    public Game() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.mQuestions);
        dest.writeTypedList(this.mPlayers);
        dest.writeInt(this.mRoomId);
    }

    protected Game(Parcel in) {
        this.mQuestions = in.createTypedArrayList(Question.CREATOR);
        this.mPlayers = in.createTypedArrayList(User.CREATOR);
        this.mRoomId = in.readInt();
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel source) {
            return new Game(source);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
}
