package io.github.boldijar.cosasapp.server;


import io.github.boldijar.cosasapp.data.BaseResponse;
import io.github.boldijar.cosasapp.data.LoginBody;
import io.github.boldijar.cosasapp.data.LoginResponse;
import io.github.boldijar.cosasapp.data.RoomResponse;
import io.github.boldijar.cosasapp.data.RoomsResponse;
import io.github.boldijar.cosasapp.data.UsersResponse;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Paul
 * @since 2018.10.12
 */
public interface ApiService {

    @POST("login")
    Observable<LoginResponse> login(@Body LoginBody body);

    @GET("users")
    Observable<UsersResponse> getUsers(@Query("name") String name);

    @POST("rooms")
    Observable<RoomResponse> createRoom();

    @POST("rooms/{room_id}/leave")
    Observable<BaseResponse> leaveRoom(@Path("room_id") int roomId);

    @GET("rooms")
    Observable<RoomsResponse> getRooms();

    @POST("rooms/join/{room_id}")
    Observable<BaseResponse> joinRoom(@Path("room_id") int roomId);

}
