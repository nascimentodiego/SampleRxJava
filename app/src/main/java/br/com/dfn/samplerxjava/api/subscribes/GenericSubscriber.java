package br.com.dfn.samplerxjava.api.subscribes;

import rx.Subscriber;

public class GenericSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(T t) {

    }
}
