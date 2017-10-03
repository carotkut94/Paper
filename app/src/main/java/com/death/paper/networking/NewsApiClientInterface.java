package com.death.paper.networking;

import com.death.paper.model.News;
import com.death.paper.model.Sources;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by rajora_sd on 21-09-2017.
 */

public interface NewsApiClientInterface {
    // https://newsapi.org/v1/sources
    @GET("sources")
    Call<Sources> getSources();
    // https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=44283b117d784997a4edd8875c900f5d
    @GET("articles")
    Call<News> getNewsArticlesFromSource(@Query("source") String from, @Query("apiKey") String apiKey);
}
