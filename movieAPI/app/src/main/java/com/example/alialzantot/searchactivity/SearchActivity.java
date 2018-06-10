package com.example.alialzantot.searchactivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.alialzantot.R;
import com.example.alialzantot.customlist.CustomAdapter;
import com.example.alialzantot.customlist.EndlessScrollListener;
import com.example.alialzantot.details.PersonDetailsActivity;
import com.example.alialzantot.home.MainActivity;
import com.example.alialzantot.retrofit.api.ApiService;
import com.example.alialzantot.retrofit.beans.PopularPeoplePojo;
import com.example.alialzantot.retrofit.beans.Result;
import com.example.alialzantot.retrofit.helper.RetroClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private PopularPeoplePojo popularPeoplePojo;
    private List<Result> persons;
    private ProgressDialog pDialog;
    ListView listView;
    CustomAdapter customAdapter;
    String searchQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent getDataIntent=getIntent();
        searchQuery=getDataIntent.getStringExtra("query");


        listView = findViewById(R.id.searchList);

        pDialog = new ProgressDialog(SearchActivity.this);
        pDialog.setMessage("Loading Data.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        //Creating an object of our api interface
        ApiService api = RetroClient.getApiService();

        /**
         * Calling JSON
         */
        Call<PopularPeoplePojo> call = api.getSearchResult(searchQuery,1);

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

                    popularPeoplePojo = response.body();
                    persons = popularPeoplePojo.getResults();

                    customAdapter = new CustomAdapter(SearchActivity.this, R.layout.single_row, R.id.name, persons);

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

                Intent detailsIntent = new Intent(getApplicationContext(), PersonDetailsActivity.class);
                detailsIntent.putExtra("personId", persons.get(i).getId() + "");
                startActivity(detailsIntent);
            }
        });

        listView.setOnScrollListener(new EndlessScrollListener(5,2) {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                loadNextDataFromApi(page);

                // or loadNextDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem=menu.findItem(R.id.serachMenuItem);
        final SearchView searchView=(SearchView) searchItem.getActionView();

        //change search icon color
        int searchIconId = searchView.getContext().getResources().getIdentifier("android:id/search_button",null, null);
        ImageView searchIcon = (ImageView) searchView.findViewById(searchIconId);
        searchIcon.setImageResource(R.drawable.ic_search_white_24dp);

        //change search icon text color
        searchView.setQueryHint("Type search name...");
        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);
        if (searchPlate!=null) {
            int searchTextId = searchPlate.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            TextView searchText = (TextView) searchPlate.findViewById(searchTextId);
            if (searchText!=null) {
                searchText.setTextColor(Color.BLACK);
                searchText.setHintTextColor(Color.BLACK);
            }
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                SearchActivity.this.searchQuery=s;
                persons.clear();
                customAdapter.clear();


                pDialog = new ProgressDialog(SearchActivity.this);
                pDialog.setMessage("Loading Data.. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();

                //Creating an object of our api interface
                ApiService api = RetroClient.getApiService();

                /**
                 * Calling JSON
                 */
                Call<PopularPeoplePojo> call = api.getSearchResult(searchQuery,1);

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

                            popularPeoplePojo = response.body();
                            persons = popularPeoplePojo.getResults();

                            customAdapter.addAll(persons);
                        }
                    }

                    @Override
                    public void onFailure(Call<PopularPeoplePojo> call, Throwable t) {
                        //persons=new ArrayList<Result>();
                        pDialog.dismiss();
                    }
                });




                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });


        return true;
    }




    public void loadNextDataFromApi(int pageIndex){



        pDialog = new ProgressDialog(SearchActivity.this);
        pDialog.setMessage("Loading Data.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        //Creating an object of our api interface
        ApiService api = RetroClient.getApiService();

        /**
         * Calling JSON
         */
        Call<PopularPeoplePojo> call = api.getSearchResult(searchQuery,pageIndex);

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

                    popularPeoplePojo = response.body();
                    persons.addAll(popularPeoplePojo.getResults());

                    customAdapter.addAll(popularPeoplePojo.getResults());
                }
            }

            @Override
            public void onFailure(Call<PopularPeoplePojo> call, Throwable t) {
                //persons=new ArrayList<Result>();
                pDialog.dismiss();
            }
        });


    }
}
