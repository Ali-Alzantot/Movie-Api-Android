package com.example.alialzantot.topmoviesactors;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.alialzantot.R;
import com.example.alialzantot.customlist.CustomAdapter;
import com.example.alialzantot.customlist.EndlessScrollListener;
import com.example.alialzantot.details.PersonDetailsActivity;
import com.example.alialzantot.home.MainActivity;
import com.example.alialzantot.retrofit.api.ApiService;
import com.example.alialzantot.retrofit.beans.Cast;
import com.example.alialzantot.retrofit.beans.MovieCast;
import com.example.alialzantot.retrofit.beans.PersonResult;
import com.example.alialzantot.retrofit.beans.PopularPeoplePojo;
import com.example.alialzantot.retrofit.beans.TopRatedMovies;
import com.example.alialzantot.retrofit.beans.TopRatedMoviesResult;
import com.example.alialzantot.retrofit.helper.RetroClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopMoviesActors extends AppCompatActivity {

    private TopRatedMovies topRatedMovies;
    private MovieCast movieCast;
    private List<Cast> cast;
    private List<Cast> filteredCast;
    private List<TopRatedMoviesResult> movies;
    private ProgressDialog pDialog;
    ListView listView;
    private List<PersonResult> persons;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_movies_actors);


        listView = findViewById(R.id.topMoviesActorsList);

        pDialog = new ProgressDialog(TopMoviesActors.this);
        pDialog.setMessage("Loading Data.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        //Creating an object of our api interface
        ApiService api = RetroClient.getApiService();

        /**
         * Calling JSON
         */
        Call<TopRatedMovies> call = api.getTopRatedMoviesJSON();

        /**
         * Enqueue Callback will be call when get response...
         */
        call.enqueue(new Callback<TopRatedMovies>() {
            @Override
            public void onResponse(Call<TopRatedMovies> call, Response<TopRatedMovies> response) {


                if (response.isSuccessful()) {
                    /**
                     * Got Successfully
                     */

                    TopMoviesActors.this.topRatedMovies = response.body();
                    movies=topRatedMovies.getTopRatedMoviesResults();
                    getMoviesCast();
                }
            }

            @Override
            public void onFailure(Call<TopRatedMovies> call, Throwable t) {
                //persons=new ArrayList<PersonResult>();
                pDialog.dismiss();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent detailsIntent = new Intent(getApplicationContext(), PersonDetailsActivity.class);
                detailsIntent.putExtra("personId", persons.get(i).getId() + "");
                startActivity(detailsIntent);
            }
        });




    }


    private void getMoviesCast(){
        int acc=0;
        cast=new ArrayList<Cast>();
        filteredCast=new ArrayList<Cast>();
        persons=new ArrayList<PersonResult>();

        loadMovieCastFromApi(movies.get(0).getId(),acc++);


    }




    public void loadMovieCastFromApi(int movieId, final int acc){


        //Creating an object of our api interface
        ApiService api = RetroClient.getApiService();

        /**
         * Calling JSON
         */
        Call<MovieCast> call = api.getMovieCastJSON(movieId);

        /**
         * Enqueue Callback will be call when get response...
         */
        call.enqueue(new Callback<MovieCast>() {
            @Override
            public void onResponse(Call<MovieCast> call, Response<MovieCast> response) {


                if (response.isSuccessful()) {
                    /**
                     * Got Successfully
                     */

                    //Dismiss Dialog

                    movieCast= response.body();
                    cast.addAll(movieCast.getCast());

                    if(acc < 19){
                        int nextCall=acc+1;
                        loadMovieCastFromApi(movies.get(nextCall).getId(),nextCall);
                    }

                    if(acc == 19){

                        for (int i=0;i<cast.size();++i){
                            int count=0;
                            for (int j=i+1;j<cast.size();++j){
                                if(cast.get(i).getId()==cast.get(j).getId()) {
                                    count++;
                                }
                            }
                            if(count>0)
                                filteredCast.add(cast.get(i));
                        }

                        for(int i=0;i<filteredCast.size();++i){
                            PersonResult temp=new PersonResult();
                            temp.setId(filteredCast.get(i).getId());
                            temp.setName(filteredCast.get(i).getName());
                            temp.setProfilePath(filteredCast.get(i).getProfilePath());
                            persons.add(temp);
                        }

                        customAdapter = new CustomAdapter(TopMoviesActors.this, R.layout.single_row, R.id.name, persons);

                        listView.setAdapter(customAdapter);

                        pDialog.dismiss();

                    }

                }
            }

            @Override
            public void onFailure(Call<MovieCast> call, Throwable t) {
                //persons=new ArrayList<PersonResult>();

            }
        });


    }
}
