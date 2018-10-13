package io.github.boldijar.cosasapp.server;


import io.github.boldijar.cosasapp.data.LoginBody;
import io.github.boldijar.cosasapp.data.LoginResponse;
import io.github.boldijar.cosasapp.data.UsersResponse;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

}
