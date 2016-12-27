package br.com.dfn.samplerxjava.api.observables;


import br.com.dfn.samplerxjava.api.ApiRequest;
import br.com.dfn.samplerxjava.api.ServiceClient;
import rx.Observable;

public abstract class GenericObservable<T>  {
    protected Observable<T> myObservable;
    protected ApiRequest apiRequest;

    public GenericObservable() {
        this.apiRequest = ServiceClient.getBuilderRetrofit().create(ApiRequest.class);
    }

    public void setObservable(Observable<T> myObservable){
        this.myObservable = myObservable;
    }

    public Observable<T> getObservable(){
        return this.myObservable;
    }


}
