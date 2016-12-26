package br.com.dfn.samplerxjava.api;


import br.com.dfn.samplerxjava.model.News;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiRequest {

    @GET("articles?source=espn&sortBy=top")
    Call<News> getEspnNews(@Query("apiKey") String apiKey);

    @GET("articles?source=espn&sortBy=top")
    Observable<News> getEspnNews2(@Query("apiKey") String apiKey);
}
