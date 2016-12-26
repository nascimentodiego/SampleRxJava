package br.com.dfn.samplerxjava.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.List;

import br.com.dfn.samplerxjava.R;
import br.com.dfn.samplerxjava.api.request.GetEspnNews;
import br.com.dfn.samplerxjava.model.Article;
import br.com.dfn.samplerxjava.model.News;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "DIEGO";
    private GetEspnTask getEspnTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /*getEspnTask = new GetEspnTask();
        getEspnTask.execute();*/

        try {
            callRxJava();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void callRxJava() throws IOException {
        final GetEspnNews getEspnNews = new GetEspnNews();

        Observable<Response<News>> myObservable = Observable.create(
                new Observable
                        .OnSubscribe<Response<News>>() {
                    @Override
                    public void call(Subscriber<? super Response<News>> sub) {
                        Log.d(TAG,"Observable::call");
                        try {
                            Response<News> news = getEspnNews.doRequest();
                            Thread.sleep(5000);
                            sub.onNext(news);
                            sub.onCompleted();
                        } catch (Exception e) {
                            Log.d(TAG,"IOException:: "+ e);
                            sub.onError(e);
                        }

                    }
                }
        ).subscribeOn(Schedulers.io());

        Subscriber<Response<News>> mySubscriber = new Subscriber<Response<News>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,"Subscriber::onError "+ e);
            }

            @Override
            public void onNext(Response<News> newsResponse) {
                News news =  newsResponse.body();
                for (Article art:news.articles) {
                    Log.d(TAG,"title-art: "+ art.title);
                }

                Log.d(TAG,"RESULT: "+ newsResponse.body());
            }

        };


        myObservable.subscribe(mySubscriber);


    }

    private class GetEspnTask extends AsyncTask<Void, Void, News> {
        GetEspnNews getEspnNews = new GetEspnNews();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected News doInBackground(Void... params) {
            Response<News> news = null;
            Log.v(TAG, "doInBackground");
            try {
                news = getEspnNews.doRequest();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return news.body();
        }

        protected void onPostExecute(News result) {
            Log.v(TAG, "onPostExecute:" + result);
        }
    }
}
