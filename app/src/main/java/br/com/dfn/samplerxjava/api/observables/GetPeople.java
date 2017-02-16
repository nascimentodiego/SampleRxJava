package br.com.dfn.samplerxjava.api.observables;

import android.util.Log;

import br.com.dfn.samplerxjava.api.ApiRequest;
import br.com.dfn.samplerxjava.api.ServiceClient;
import br.com.dfn.samplerxjava.model.startwars.Actor;
import br.com.dfn.samplerxjava.model.startwars.Movie;
import br.com.dfn.samplerxjava.model.startwars.PeopleResult;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class GetPeople extends GenericObservable {


    private static final String TAG = "DIEGO";

    public GetPeople() {
        this.apiRequest = ServiceClient.getBuilderStarWarsRetrofit().create(ApiRequest.class);

        myObservable = apiRequest.getPeople()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<PeopleResult>() {
                    @Override
                    public void call(PeopleResult result) {
                        Log.d(TAG, "--------------------- 1º Transformation: -------------------------- ");
                        for (Actor actor : result.results) {
                            Log.d(TAG, "actor: " + actor.name);
                        }

                    }
                })
                .flatMap(new Func1<PeopleResult, Observable<Actor>>() {
                    @Override
                    public Observable<Actor> call(PeopleResult result) {
                        return Observable.from(result.results);
                    }
                })
                .doOnNext(new Action1<Actor>() {
                    @Override
                    public void call(Actor actor) {
                        Log.d(TAG, "--------------------- 2º Transformation: -------------------------- ");
                        for (String urlFilm : actor.films) {
//                            Log.d(TAG, "urlFilm: " + urlFilm);
                        }
                    }
                })
                .flatMap(new Func1<Actor, Observable<String>>() {
                    @Override
                    public Observable<String> call(Actor result) {
                        return Observable.from(result.films);
                    }
                })
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<String, Observable<Movie>>() {
                    @Override
                    public Observable<Movie> call(String urlFilm) {
                        String filmId = formatString(urlFilm);
                        Log.d(TAG, "urlFilm2: " + urlFilm);
                        return apiRequest.getMovie(filmId);
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d(TAG, "throwable: " + throwable.getMessage());
                    }
                });
    }

    public String formatString(String s) {
        return s.replace("http://swapi.co/api/films/", "").replace("/", "");
    }

    public Observable<PeopleResult> getObservable() {
        return this.myObservable;
    }
}
