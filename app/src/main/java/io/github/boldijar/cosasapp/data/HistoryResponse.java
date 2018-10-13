package io.github.boldijar.cosasapp.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * TODO: Class description
 *
 * @author Paul
 * @since 2018.10.13
 */
public class HistoryResponse extends BaseResponse {
    @SerializedName("data")
    public List<RoomHistory> mRoomHistoryList;
}
