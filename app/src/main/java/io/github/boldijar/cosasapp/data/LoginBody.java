package io.github.boldijar.cosasapp.data;

import com.google.gson.annotations.SerializedName;

/**
 * @author Paul
 * @since 2018.10.13
 */
public class LoginBody {

    @SerializedName("password")
    private String mPassword;
    @SerializedName("email")
    private String mEmail;

    public LoginBody(String password, String email) {
        mPassword = password;
        mEmail = email;
    }
}
