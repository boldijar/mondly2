package io.github.boldijar.cosasapp;

import android.app.Application;

import com.google.firebase.FirebaseApp;

import io.github.boldijar.cosasapp.util.Prefs;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import timber.log.Timber;

/**
 * @author Paul
 * @since 2018.10.12
 */
public class ShaormApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Prefs.init(this);
        FirebaseApp.initializeApp(this);
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("Poppins-Regular.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        Timber.plant(new Timber.DebugTree());
    }
}
