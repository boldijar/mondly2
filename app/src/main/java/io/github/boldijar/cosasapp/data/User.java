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
}
