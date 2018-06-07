package jsonretrofit.api;

import jsonretrofit.beans.PersonDetails;
import jsonretrofit.beans.PopularPeoplePojo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Ali Alzantot on 07/06/2018.
 */

public interface ApiService {



    @GET("/3/person/popular?api_key=fc3140c227807880f6ba2c7bce9d1cb5&language=en-US&page=1")
    Call<PopularPeoplePojo> getMyJSON();

    @GET("/3/person/{personId}?api_key=fc3140c227807880f6ba2c7bce9d1cb5&language=en-US")
    Call<PersonDetails> getPersonDetails(@Path("personId") int personId);

}
