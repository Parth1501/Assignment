package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;




import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import model.Country;
import network.Utils;

public class CountryDetails extends AppCompatActivity {
    static String TAG="Second Activity";
    private TextView name;
    private TextView capital;
    private TextView region;
    private TextView subRegion;
    private TextView population;
    private TextView language;
    private TextView border;
    private Country country;
    private ImageView imageView;
    private String[] languageArray;
    private String languageStr;
    private String borderStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);
        init();
        try {
            setData();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private void setData() throws IOException {
        Log.e(TAG, "setData: "+country.getFlag() );
        Utils.fetchSvg(this,country.getFlag(),imageView);
        name.setText(country.getName());
        capital.setText(country.getCapital());
        region.setText(country.getRegion());
        subRegion.setText(country.getSubregion());
        population.setText(String.valueOf(country.getPopulation()));
        language.setText(languageStr);
        if(borderStr.equals("")) {
            border.setText("None");
        }
        else {
            border.setText(borderStr);
        }
    }
    private  void init() {
        Intent intent=getIntent();
        country=intent.getParcelableExtra("DETAILS");
        languageArray=intent.getStringArrayExtra("LANGUAGES");
        name=findViewById(R.id.country_name);
        capital=findViewById(R.id.country_capital);
        region=findViewById(R.id.country_region);
        subRegion=findViewById(R.id.country_subregion);
        population=findViewById(R.id.country_population);
        imageView=findViewById(R.id.country_flag);
        language=findViewById(R.id.country_languages);
        border=findViewById(R.id.country_borders);
        languageStr=new String();
        borderStr=new String();
        for(int i=0;i<languageArray.length;i++) {
                if(i==languageArray.length-1) {
                    languageStr += languageArray[i];
                }
                else {
                    languageStr += languageArray[i] + "\n";
                }
        }
        for(int i=0;i<country.getBorders().size();i++) {
            borderStr += country.getBorders().get(i) + "\n";
        }


    }
}