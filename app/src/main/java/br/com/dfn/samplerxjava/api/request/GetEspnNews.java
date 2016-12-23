package br.com.dfn.samplerxjava.api.request;

import java.io.IOException;

import br.com.dfn.samplerxjava.api.ApiRequest;
import br.com.dfn.samplerxjava.api.ServiceClient;
import br.com.dfn.samplerxjava.model.News;
import retrofit2.Call;
import retrofit2.Response;

public class GetEspnNews {

    public Response<News> doRequest() throws IOException {
        ApiRequest apiRequest = ServiceClient.getBuilderRetrofit().create(ApiRequest.class);
        Response<News> execute;

            Call<News> call = apiRequest.getEspnNews(ServiceClient.API_KEY);
            execute = call.execute();
        return execute;
    }

}
