package com.example.alialzantot.movieapi;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import customlist.CustomAdapter;
import customlist.Person;
import jsonretrofit.api.ApiService;
import jsonretrofit.beans.PopularPeoplePojo;
import jsonretrofit.beans.Result;
import jsonretrofit.helper.RetroClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private PopularPeoplePojo popularPeoplePojo;
    private List<Result> persons;
    private ProgressDialog pDialog;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.myList);

        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Loading Data.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        //Creating an object of our api interface
        ApiService api = RetroClient.getApiService();

        /**
         * Calling JSON
         */
        Call<PopularPeoplePojo> call = api.getMyJSON();

        /**
         * Enqueue Callback will be call when get response...
         */
        call.enqueue(new Callback<PopularPeoplePojo>() {
            @Override
            public void onResponse(Call<PopularPeoplePojo> call, Response<PopularPeoplePojo> response) {


                if (response.isSuccessful()) {
                    /**
                     * Got Successfully
                     */

                    //Dismiss Dialog
                    pDialog.dismiss();

                    popularPeoplePojo=response.body();
                    persons=popularPeoplePojo.getResults();

                    CustomAdapter customAdapter=new CustomAdapter(MainActivity.this,R.layout.single_row,R.id.name,persons);

                    listView.setAdapter(customAdapter);
                }
            }

            @Override
            public void onFailure(Call<PopularPeoplePojo> call, Throwable t) {
                //persons=new ArrayList<Result>();
                pDialog.dismiss();
            }
        });




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(persons.get(i).getName());
            }
        });

    }
}
