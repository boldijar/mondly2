package io.github.boldijar.cosasapp.data;

import com.google.gson.annotations.SerializedName;

/**
 * @author Paul
 * @since 2018.10.13
 */
public class User {

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
}
