package com.example.alialzantot.details;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alialzantot.R;
import com.example.alialzantot.customlist.CustomAdapter;
import com.example.alialzantot.home.MainActivity;
import com.example.alialzantot.retrofit.api.ApiService;
import com.example.alialzantot.retrofit.beans.PersonDetails;
import com.example.alialzantot.retrofit.beans.PopularPeoplePojo;
import com.example.alialzantot.retrofit.helper.RetroClient;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonDetailsActivity extends AppCompatActivity {

    int personId;
    private ProgressDialog pDialog;
    PersonDetails personDetails;
    TextView nameTextView,birthDayTextView,placeOfBirthTextView,bioTextView;
    ImageView profileImage;
    Button showPersonImagesButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent getDataIntent=getIntent();
        personId=Integer.parseInt(getDataIntent.getStringExtra("personId"));


        pDialog = new ProgressDialog(PersonDetailsActivity.this);
        pDialog.setMessage("Loading Data.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        //Creating an object of our api interface
        ApiService api = RetroClient.getApiService();

        /**
         * Calling JSON
         */
        Call<PersonDetails> call = api.getPersonDetails(personId);

        /**
         * Enqueue Callback will be call when get response...
         */
        call.enqueue(new Callback<PersonDetails>() {
            @Override
            public void onResponse(Call<PersonDetails> call, Response<PersonDetails> response) {


                if (response.isSuccessful()) {
                    /**
                     * Got Successfully
                     */

                    //Dismiss Dialog
                    pDialog.dismiss();
                    personDetails=response.body();

                    setContentView(R.layout.activity_person_details);
                    nameTextView=findViewById(R.id.nameTF);
                    bioTextView=findViewById(R.id.bioTF);
                    birthDayTextView=findViewById(R.id.birthDayTF);
                    placeOfBirthTextView=findViewById(R.id.placeOfBirthTF);
                    profileImage=findViewById(R.id.profileImg);

                    showPersonImagesButton=findViewById(R.id.showPersonImagesButton);

                    showPersonImagesButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent detailsIntent=new Intent(getApplicationContext(), ImagesGridView.class);
                            detailsIntent.putExtra("personId",personId+"");
                            startActivity(detailsIntent);
                        }
                    });

                    nameTextView.setText(personDetails.getName());
                    placeOfBirthTextView.setText(personDetails.getPlaceOfBirth());
                    birthDayTextView.setText(personDetails.getBirthday());
                    bioTextView.setText(personDetails.getBiography());
                    Picasso.with(PersonDetailsActivity.this).load("http://image.tmdb.org/t/p/w300/"+personDetails.getProfilePath()).into(profileImage);

                }
            }

            @Override
            public void onFailure(Call<PersonDetails> call, Throwable t) {

                pDialog.dismiss();
                finish();
            }
        });



    }
}
