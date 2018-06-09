package com.example.alialzantot.retrofit.api;

import com.example.alialzantot.retrofit.beans.PersonDetails;
import com.example.alialzantot.retrofit.beans.PersonImages;
import com.example.alialzantot.retrofit.beans.PopularPeoplePojo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Ali Alzantot on 07/06/2018.
 */

public interface ApiService {



    @GET("/3/person/popular?api_key=fc3140c227807880f6ba2c7bce9d1cb5&language=en-US")
    Call<PopularPeoplePojo> getMyJSON(@Query("page") int page);


    @GET("/3/search/person?api_key=fc3140c227807880f6ba2c7bce9d1cb5&search_type=ngram")
    Call<PopularPeoplePojo> getSearchResult(@Query("query") String query,@Query("page") int page);

    @GET("/3/person/{personId}?api_key=fc3140c227807880f6ba2c7bce9d1cb5&language=en-US")
    Call<PersonDetails> getPersonDetails(@Path("personId") int personId);

    @GET("3/person/{personId}/images?api_key=fc3140c227807880f6ba2c7bce9d1cb5")
    Call<PersonImages> getPersonImages(@Path("personId") int personId);

}
