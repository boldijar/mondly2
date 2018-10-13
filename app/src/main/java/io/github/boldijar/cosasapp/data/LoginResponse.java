package io.github.boldijar.cosasapp.data;

import com.google.gson.annotations.SerializedName;

/**
 * TODO: Class description
 *
 * @author Paul
 * @since 2018.10.13
 */
public class LoginResponse extends BaseResponse {

    @SerializedName("token")
    private String mToken;

    public String getToken() {
        return mToken;
    }
}
