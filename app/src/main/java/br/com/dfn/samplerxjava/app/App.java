package br.com.dfn.samplerxjava.app;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static Context mContext = null;

    public static Context getContext() {
        return mContext;
    }

    private static void setContext(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setContext(getApplicationContext());
    }
}
