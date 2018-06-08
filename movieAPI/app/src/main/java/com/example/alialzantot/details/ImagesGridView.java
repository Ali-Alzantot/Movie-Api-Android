package com.example.alialzantot.details;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.example.alialzantot.R;
import com.example.alialzantot.gridview.GridViewCustomAdapter;
import com.example.alialzantot.retrofit.api.ApiService;
import com.example.alialzantot.retrofit.beans.PersonDetails;
import com.example.alialzantot.retrofit.beans.PersonImages;
import com.example.alialzantot.retrofit.helper.RetroClient;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImagesGridView extends AppCompatActivity {

    private int personId;
    private ProgressDialog pDialog;
    private PersonImages personImages;
    GridView personImagesGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent getDataIntent=getIntent();
        personId=Integer.parseInt(getDataIntent.getStringExtra("personId"));


        pDialog = new ProgressDialog(ImagesGridView.this);
        pDialog.setMessage("Loading Data.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        //Creating an object of our api interface
        ApiService api = RetroClient.getApiService();

        /**
         * Calling JSON
         */
        Call<PersonImages> call = api.getPersonImages(personId);

        /**
         * Enqueue Callback will be call when get response...
         */
        call.enqueue(new Callback<PersonImages>() {
            @Override
            public void onResponse(Call<PersonImages> call, Response<PersonImages> response) {


                if (response.isSuccessful()) {
                    /**
                     * Got Successfully
                     */

                    //Dismiss Dialog
                    pDialog.dismiss();
                    personImages=response.body();

                    setContentView(R.layout.activity_images_grid_view);

                    personImagesGridView=findViewById(R.id.imagesGridView);

                    personImagesGridView.setAdapter(new GridViewCustomAdapter(ImagesGridView.this, personImages.getProfiles()));


                }
            }

            @Override
            public void onFailure(Call<PersonImages> call, Throwable t) {

                pDialog.dismiss();
                finish();
            }
        });




    }
}
