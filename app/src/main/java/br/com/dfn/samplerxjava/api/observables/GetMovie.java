package br.com.dfn.samplerxjava.api.observables;

import br.com.dfn.samplerxjava.api.ApiRequest;
import br.com.dfn.samplerxjava.api.ServiceClient;
import br.com.dfn.samplerxjava.model.startwars.Movie;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GetMovie extends GenericObservable  {


    private static final String TAG = "DIEGO";

    public GetMovie() {
        this.apiRequest = ServiceClient.getBuilderStarWarsRetrofit().create(ApiRequest.class);

        myObservable = apiRequest.getMovie("6")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<Movie> getObservable() {
        return this.myObservable;
    }
}
