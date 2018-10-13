package io.github.boldijar.cosasapp.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Paul
 * @since 2018.10.13
 */
public class GameStatsResponse extends BaseResponse {

    @SerializedName("data")
    public List<User> mUsers;


}
