package io.github.boldijar.cosasapp.util;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 *
 * @author Paul
 * @since 2018.10.13
 */
public class ObservatorulNormal<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onComplete() {

    }
}
