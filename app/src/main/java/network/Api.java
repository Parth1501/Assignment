package network;


import java.util.ArrayList;


import model.Country;

import retrofit2.Call;

import retrofit2.http.GET;

public interface Api{
    String BASE_URL="https://restcountries.eu/rest/v2/";
    @GET("all")
    Call<ArrayList<Country>> getDetails();

}
