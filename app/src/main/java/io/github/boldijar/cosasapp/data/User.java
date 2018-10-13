package io.github.boldijar.cosasapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author Paul
 * @since 2018.10.13
 */
public class User implements Parcelable {

    @SerializedName("api_token")
    public String mToken;
    @SerializedName("id")
    public int mId;
    @SerializedName("email")
    public String mEmail;
    @SerializedName("image")
    public String mImage;
    @SerializedName("name")
    public String mName;

    @SerializedName("score")
    public int mScore;
    @SerializedName("question_answered_count")
    public int mQuestionAnsweredCount;
    @SerializedName("question_answered_total")
    public int mQuestionAnsweredTotal;
    @SerializedName("overall_score")
    public int mAllScore;


    @Override
    public String toString() {
        return "User{" +
                "mToken='" + mToken + '\'' +
                ", mId=" + mId +
                ", mEmail='" + mEmail + '\'' +
                ", mImage='" + mImage + '\'' +
                ", mName='" + mName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return mId == user.mId;
    }

    @Override
    public int hashCode() {
        return mId;
    }

    public User() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mToken);
        dest.writeInt(this.mId);
        dest.writeString(this.mEmail);
        dest.writeString(this.mImage);
        dest.writeString(this.mName);
        dest.writeInt(this.mScore);
        dest.writeInt(this.mQuestionAnsweredCount);
        dest.writeInt(this.mQuestionAnsweredTotal);
    }

    protected User(Parcel in) {
        this.mToken = in.readString();
        this.mId = in.readInt();
        this.mEmail = in.readString();
        this.mImage = in.readString();
        this.mName = in.readString();
        this.mScore = in.readInt();
        this.mQuestionAnsweredCount = in.readInt();
        this.mQuestionAnsweredTotal = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
