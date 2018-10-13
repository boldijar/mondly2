package io.github.boldijar.cosasapp.util;

import io.github.boldijar.cosasapp.data.BaseResponse;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * TODO: Class description
 *
 * @author Paul
 * @since 2018.10.13
 */
public class Observatorul<T extends BaseResponse> implements Observer<T> {
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
