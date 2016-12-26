package br.com.dfn.samplerxjava.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import br.com.dfn.samplerxjava.R;
import br.com.dfn.samplerxjava.api.observables.EspnObservable;
import br.com.dfn.samplerxjava.model.Article;
import br.com.dfn.samplerxjava.model.News;
import rx.Subscriber;

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
    public void onDestroy() {
        super.onDestroy();

        if (mySubscriber != null && mySubscriber.isUnsubscribed()) {
            mySubscriber.unsubscribe();
        }
    }
}
