package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import Database.CountryDao;
import Database.CountryDatabase;
import model.Country;
import network.Api;
import network.RetrofitAPI;
import retrofit2.Call;

import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private static String TAG = "Check My Code";
    private RecyclerView recyclerView;
    private CountryAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private CountryDatabase countryDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        countryDatabase = CountryDatabase.getInstance(MainActivity.this);

        Api api = RetrofitAPI.getClient().create(Api.class);
        Call<ArrayList<Country>> call = api.getDetails();

        call.enqueue(new Callback<ArrayList<Country>>() {
            @Override
            public void onResponse(Call<ArrayList<Country>> call, Response<ArrayList<Country>> response) {
                final ArrayList<Country> countries = response.body();
                Log.e(TAG, "onResponse: " + countries.get(1).getLanguages().get(0).getName());
                addToDB(countries);
                adapter = new CountryAdapter(countries);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter((adapter));
                adapter.setOnItemClickListener(new CountryAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        int languageArraySize = countries.get(position).getLanguages().size();
                        String[] languageArray = new String[languageArraySize];
                        Log.e(TAG, "onItemClick: "+languageArray.length );
                        for (int i = 0; i < languageArraySize; i++) {
                            languageArray[i] = countries.get(position).getLanguages().get(i).getName();

                        }
                        Intent intent = new Intent(MainActivity.this, CountryDetails.class);
                        intent.putExtra("DETAILS", countries.get(position));
                        intent.putExtra("LANGUAGES", languageArray);
                        startActivity(intent);

                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<Country>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                getData();


            }
        });
    }

    private void getData() {
        final Toast showToast=Toast.makeText(MainActivity.this,"No data Found",Toast.LENGTH_SHORT);
        Thread thread = new Thread() {
            ArrayList<Country> adapterCountry = new ArrayList<>();
            ArrayList<String> addBorder = new ArrayList<>();
            Country.Languages l;

            @Override
            public void run() {
                super.run();
                String[] languageString;
                String[] borderString;
                ArrayList<Country.Languages> addLanguage = new ArrayList<>();
                final List<Database.Country> countries = countryDatabase.countryDao().getAll();
                if (countries.size() == 0) {
                    showToast.show();
                } else {
                    for (int i = 0; i < countries.size(); i++) {
                        borderString = countries.get(i).getBorders().split("\n");
                        for (int j = 0; j < borderString.length; j++) {
                            addBorder.add(borderString[j]);
                        }

                        Country.Languages l = new Country.Languages();
                        l.setName(countries.get(i).getLanguages());
                        addLanguage.add(l);


                        String name = countries.get(i).getName();
                        String capital = countries.get(i).getCapital();
                        String flag = countries.get(i).getFlag();
                        String region = countries.get(i).getRegion();
                        String subregion = countries.get(i).getSubregion();
                        int population = countries.get(i).getPopulation();
                        Country c = new Country(name, capital, flag, region, subregion, population, addBorder, addLanguage);
                        addLanguage.clear();
                        addBorder.clear();
                        adapterCountry.add(c);
//                    Log.e(TAG, "run: "+count+"ADDED" );

                    }

                    adapter = new CountryAdapter(adapterCountry);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter((adapter));
                    adapter.setOnItemClickListener(new CountryAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            int languageArraySize = adapterCountry.get(position).getLanguages().size();
                            String[] languageArray = new String[languageArraySize];
                            for (int i = 0; i < languageArraySize; i++) {
                                languageArray[i] = adapterCountry.get(position).getLanguages().get(i).getName();
                            }
                            Intent intent = new Intent(MainActivity.this, CountryDetails.class);
                            intent.putExtra("DETAILS", adapterCountry.get(position));
                            intent.putExtra("LANGUAGES", languageArray);
                            startActivity(intent);

                        }
                    });

                }
            }
        };

        thread.start();
    }


    private void addToDB(final ArrayList<Country> countries) {

        SharedPreferences sharedPreferences = getSharedPreferences("ALREADY", MODE_PRIVATE);
        String str = sharedPreferences.getString("EXISTS", "");


        Thread t = new Thread() {
            @Override
            public void run() {
                int count = 0;
                String languageString = new String();
                String borderString = new String();
                for (int i = 0; i < countries.size(); i++) {
                    for (int j = 0; j < countries.get(i).getLanguages().size(); j++) {
                        languageString += countries.get(i).getLanguages().get(j) + "\n";
                    }
                    for (int j = 0; j < countries.get(i).getBorders().size(); j++) {
                        borderString += countries.get(i).getBorders().get(j) + "\n";
                    }
                    String name = countries.get(i).getName();
                    String capital = countries.get(i).getCapital();
                    String flag = countries.get(i).getFlag();
                    String region = countries.get(i).getRegion();
                    String subregion = countries.get(i).getSubregion();
                    int population = countries.get(i).getPopulation();
                    Database.Country addCountry = new Database.Country(name, flag, capital, region, subregion, population, languageString, borderString);
                    countryDatabase.countryDao().insert(addCountry);
                    Log.e(TAG, "run: " + count + "ADDED");
                    count++;

                }
            }
        };
        if (str.equals("")) {
            t.start();
            sharedPreferences.edit().putString("EXISTS", "true").commit();
            toast("Successfully Store in Local Database ");
        }

    }

    void toast(String str) {
        Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();

    }

    public void DeleteData(View view) {
        final Toast showToast=Toast.makeText(MainActivity.this,"Successfully Deleted",Toast.LENGTH_LONG);
        final Toast unableToDeleteToast=Toast.makeText(MainActivity.this,"No Data To Delete",Toast.LENGTH_LONG);
        Thread thread=new Thread() {
            @Override
            public void run() {
                countryDatabase.countryDao().delete();
                SharedPreferences sharedPreferences=getSharedPreferences("ALREADY",MODE_PRIVATE);
                if(sharedPreferences.getString("EXISTS","").equals("")) {
                    unableToDeleteToast.show();
                }
                else {
                    sharedPreferences.edit().putString("EXISTS", "").commit();
                    showToast.show();
                }
            }
        };
        thread.start();



    }
}