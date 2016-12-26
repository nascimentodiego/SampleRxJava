package br.com.dfn.samplerxjava.api.observables;

import br.com.dfn.samplerxjava.api.ServiceClient;
import br.com.dfn.samplerxjava.model.News;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EspnObservable extends GenericObservable {

    private Observable<News> myObservable;

    public EspnObservable(){
        myObservable = apiRequest.getEspnNews2(ServiceClient.API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<News> getObservable(){
        return this.myObservable;
    }
}
