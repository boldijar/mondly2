package io.github.boldijar.cosasapp.server;


import io.github.boldijar.cosasapp.data.LoginBody;
import io.github.boldijar.cosasapp.data.LoginResponse;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author Paul
 * @since 2018.10.12
 */
public interface ApiService {

    @POST("login")
    Observable<LoginResponse> login(@Body LoginBody body);

}
