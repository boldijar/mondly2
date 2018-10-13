package io.github.boldijar.cosasapp.data;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Paul
 * @since 2018.10.13
 */
public class BaseResponse {

    @SerializedName("success")
    private boolean mSuccess;

    public boolean isSuccess() {
        return mSuccess;
    }
}
