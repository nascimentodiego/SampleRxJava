package br.com.dfn.samplerxjava.api.observables;

import br.com.dfn.samplerxjava.api.ServiceClient;
import br.com.dfn.samplerxjava.model.News;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EspnObservable<News> extends GenericObservable {

    public EspnObservable(){
        myObservable = apiRequest.getEspnNews2(ServiceClient.API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).cache();
    }

    public Observable<News> getObservable(){
        return this.myObservable;
    }
}
