package br.com.dfn.samplerxjava.view;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import br.com.dfn.samplerxjava.R;
import br.com.dfn.samplerxjava.api.ApiRequest;
import br.com.dfn.samplerxjava.api.ServiceClient;
import br.com.dfn.samplerxjava.api.observables.EspnObservable;
import br.com.dfn.samplerxjava.app.App;
import br.com.dfn.samplerxjava.model.Article;
import br.com.dfn.samplerxjava.model.News;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String TAG = "DIEGO";

    private TextView txtResult;
    Subscriber<News> mySubscriber;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_main, container, false);
        txtResult = (TextView)root.findViewById(R.id.txtResult);
        try {
            callRxJava2();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return root;
    }

    public void callRxJava2() throws IOException {
       EspnObservable espnObservable = new EspnObservable();


        mySubscriber = new Subscriber<News>() {
            @Override
            public void onCompleted() {
//                Toast.makeText(App.getContext(),"onCompleted",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,"Subscriber::onError "+ e);
            }

            @Override
            public void onNext(News news) {
                for (Article art:news.articles) {
                    Log.d(TAG,"title-art: "+ art.title);
                }
                txtResult.setText("RESULT: "+ news.status);
                Log.d(TAG,"RESULT: "+ news.status);
            }

        };

        espnObservable.getObservable().subscribe(mySubscriber);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(mySubscriber != null && mySubscriber.isUnsubscribed()) {
            mySubscriber.unsubscribe();
        }
    }
}
