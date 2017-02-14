package br.com.dfn.samplerxjava.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.dfn.samplerxjava.R;
import br.com.dfn.samplerxjava.api.observables.EspnObservable;
import br.com.dfn.samplerxjava.api.observables.GetMovie;
import br.com.dfn.samplerxjava.api.observables.GetPeople;
import br.com.dfn.samplerxjava.model.Article;
import br.com.dfn.samplerxjava.model.News;
import br.com.dfn.samplerxjava.model.startwars.Actor;
import br.com.dfn.samplerxjava.model.startwars.Movie;
import br.com.dfn.samplerxjava.model.startwars.PeopleResult;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivityFragment extends Fragment {

    private static final String TAG = "DIEGO";


    private TextView txtResult;
  /*  private EspnObservable espnObservable;
    private Subscriber<News> mySubscriber;*/

    private GetPeople getPeopleObservable;
    private Subscriber<Actor> myPeopleSubscriber;


    public MainActivityFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        txtResult = (TextView) root.findViewById(R.id.txtResult);

//        espnObservable = new EspnObservable();
        getPeopleObservable = new GetPeople();

        if (savedInstanceState == null) {
//            registerSubscriber();
            registerSubscribeStarWars();
        }

        return root;
    }

    public void registerSubscribeStarWars() {
        myPeopleSubscriber = new Subscriber<Actor>() {
            @Override
            public void onCompleted() {
//                Toast.makeText(App.getContext(),"onCompleted",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "Subscriber::onError " + e);
            }

            @Override
            public void onNext(Actor result) {
               /* for (Actor actor : result.films) {
                    Log.d(TAG, "actor: " + actor.name);
                }*/
            }

        };
       /* getPeopleObservable
                .getObservable()
                .subscribe();*/
//                .subscribe(myPeopleSubscriber);

        new GetMovie().getObservable().subscribe(new Action1<Movie>() {
            @Override
            public void call(Movie movie) {
                Log.d(TAG, "movie: " + movie.title);
            }
        });
    }


    public void registerSubscriber() {
      /*  mySubscriber = new Subscriber<News>() {
            @Override
            public void onCompleted() {
//                Toast.makeText(App.getContext(),"onCompleted",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "Subscriber::onError " + e);
            }

            @Override
            public void onNext(News news) {
                for (Article art : news.articles) {
                    Log.d(TAG, "title-art: " + art.title);
                }
                txtResult.setText("RESULT: " + news.status);
                Log.d(TAG, "RESULT: " + news.status);
            }

        };
        espnObservable.getObservable().subscribe(mySubscriber);*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (myPeopleSubscriber != null && myPeopleSubscriber.isUnsubscribed()) {
            myPeopleSubscriber.unsubscribe();
        }
    }
}
