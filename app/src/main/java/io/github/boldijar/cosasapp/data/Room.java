package io.github.boldijar.cosasapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Paul
 * @since 2018.10.13
 */
public class Room implements Parcelable {

    @SerializedName("id")
    public int mId;

    @SerializedName("players")
    public List<User> mPlayers;


    @SerializedName("created_at")
    public Timestamp mCreated;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeTypedList(this.mPlayers);
        dest.writeSerializable(this.mCreated);
    }

    public Room() {
    }

    protected Room(Parcel in) {
        this.mId = in.readInt();
        this.mPlayers = in.createTypedArrayList(User.CREATOR);
        this.mCreated = (Timestamp) in.readSerializable();
    }

    public static final Parcelable.Creator<Room> CREATOR = new Parcelable.Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel source) {
            return new Room(source);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };
}
