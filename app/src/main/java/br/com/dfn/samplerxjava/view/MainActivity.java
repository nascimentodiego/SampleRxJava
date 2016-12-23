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

import br.com.dfn.samplerxjava.R;
import br.com.dfn.samplerxjava.api.request.GetEspnNews;
import br.com.dfn.samplerxjava.model.News;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
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

        getEspnTask = new GetEspnTask();
        getEspnTask.execute();
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
