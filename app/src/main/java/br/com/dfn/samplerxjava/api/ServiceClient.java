package br.com.dfn.samplerxjava.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceClient {

    public static final String API_KEY = "d268ab1d89a54d01af94ec7fd5f5bbd6";
    private static final String URL = "https://newsapi.org/v1/";
    private static final String URL_STAR_WARS = "http://swapi.co/api/";

    private static Retrofit builder =
            new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

    public static Retrofit getBuilderRetrofit(){
        return builder;
    }

    private static Retrofit builderStarWars =
            new Retrofit.Builder()
                    .baseUrl(URL_STAR_WARS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

    public static Retrofit getBuilderStarWarsRetrofit(){
        return builderStarWars;
    }


}
