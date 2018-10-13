package io.github.boldijar.cosasapp.server;

import com.google.gson.GsonBuilder;

import io.github.boldijar.cosasapp.util.Prefs;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Paul
 * @since 2018.10.12
 */
public class Http {

    private static final String ENDPOINT = "http://3d083ce1.ngrok.io/api/";
    private ApiService mApiService;

    private Http() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mApiService = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd hh:mm:ss")
                        .create())
                )
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .baseUrl(ENDPOINT)
                .client(new OkHttpClient.Builder()
                        .addInterceptor(interceptor)
                        .addInterceptor(chain -> {
                            final Request original = chain.request();
                            Request.Builder builder = original.newBuilder();
                            builder = builder.header("Authorization",
                                    "Bearer " + Prefs.Token.get());
                            return chain.proceed(builder.build());
                        })
                        .build())
                .build().create(ApiService.class);
    }

    public ApiService getApiService() {
        return mApiService;
    }

    private static Http instance;

    public static Http getInstance() {
        if (instance == null) {
            instance = new Http();
        }
        return instance;
    }
}
