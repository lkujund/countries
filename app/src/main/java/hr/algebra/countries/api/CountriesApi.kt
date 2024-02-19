package hr.algebra.countries.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val API_URL = "https://restcountries.com/v3.1/all/"

interface CountriesApi {

    @GET("?fields=cca3,name,capital,flag,coatOfArms,latlng,area,population,maps,currencies,languages")
    fun fetchItems(@Query("count") count: Int = 10)
            : Call<List<CountriesItem>>

}