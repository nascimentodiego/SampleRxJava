package br.com.dfn.samplerxjava.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.android.FragmentEvent;

import br.com.dfn.samplerxjava.R;
import br.com.dfn.samplerxjava.api.observables.EspnObservable;
import br.com.dfn.samplerxjava.model.Article;
import br.com.dfn.samplerxjava.model.News;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.observers.TestSubscriber;
import rx.subjects.BehaviorSubject;

public class MainActivityFragment extends Fragment {

    private static final String TAG = "DIEGO";


    private TextView txtResult;
    private EspnObservable espnObservable;
    private Subscriber<News> mySubscriber;

    public MainActivityFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        espnObservable = new EspnObservable();
        txtResult = (TextView) root.findViewById(R.id.txtResult);

        if (savedInstanceState == null) {
            registerSubscriber();
        }

        return root;
    }

    public void registerSubscriber() {
        if (espnObservable == null) {
            espnObservable = new EspnObservable();
        }

        mySubscriber = new Subscriber<News>() {
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
        espnObservable.getObservable().subscribe(mySubscriber);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mySubscriber != null && mySubscriber.isUnsubscribed()) {
            mySubscriber.unsubscribe();
        }
    }
}
