package io.github.boldijar.cosasapp.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

/**
 * @author Paul
 * @since 2018.10.12
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}
