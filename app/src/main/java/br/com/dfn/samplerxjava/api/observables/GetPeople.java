package br.com.dfn.samplerxjava.api.observables;

import android.util.Log;

import br.com.dfn.samplerxjava.api.ApiRequest;
import br.com.dfn.samplerxjava.api.ServiceClient;
import br.com.dfn.samplerxjava.model.startwars.Actor;
import rx.Observable;
import rx.schedulers.Schedulers;

public class GetPeople extends GenericObservable {


    private static final String TAG = "DIEGO";

    public GetPeople() {
        this.apiRequest = ServiceClient.getBuilderStarWarsRetrofit().create(ApiRequest.class);

        myObservable = apiRequest.getPeople()
                .subscribeOn(Schedulers.io())
                .doOnNext(result -> {
                    Log.d(TAG, "--------------------- 1º Transformation: -------------------------- ");
                    for (Actor actor : result.results) {
                        Log.d(TAG, "actor: " + actor.name);
                    }

                })
                .flatMap(result -> Observable.from(result.results))
                .doOnNext(actor -> {
                    Log.d(TAG, "--------------------- 2º Transformation: -------------------------- ");
                    for (String urlFilm : actor.films) {
                        Log.d(TAG, "urlFilm: " + urlFilm);
                    }
                })
                .flatMap(result -> Observable.from(result.films))
                .flatMap(urlFilm -> apiRequest.getMovie(formatString(urlFilm)))
                .doOnNext(movie -> Log.d(TAG, "movie: " + movie.title))
                .doOnError(throwable -> Log.d(TAG, "throwable: " + throwable.getMessage()));
    }

    public String formatString(String s) {
        return s.replace("http://swapi.co/api/films/", "").replace("/", "");
    }

    public Observable getObservable() {
        return this.myObservable;
    }
}
