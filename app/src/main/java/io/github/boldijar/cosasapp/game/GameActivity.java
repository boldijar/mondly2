package io.github.boldijar.cosasapp.game;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.boldijar.cosasapp.R;
import io.github.boldijar.cosasapp.base.BaseActivity;
import io.github.boldijar.cosasapp.base.PersonProgress;
import io.github.boldijar.cosasapp.util.RxUtils;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author Paul
 * @since 2018.10.13
 */
public class GameActivity extends BaseActivity {

    @BindView(R.id.game_person)
    PersonProgress mPersonProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);
        mPersonProgress.setProgress(30);
        io.reactivex.Observable.interval(1, TimeUnit.SECONDS)
                .compose(RxUtils.applySchedulers())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        int rand = (int) (Math.random() * 100);
                        mPersonProgress.setProgress(rand);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
