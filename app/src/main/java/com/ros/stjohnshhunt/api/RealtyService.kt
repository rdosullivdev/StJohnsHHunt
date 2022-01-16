package com.ros.stjohnshhunt.api

import android.content.Context
import com.ros.stjohnshhunt.BuildConfig
import com.ros.stjohnshhunt.api.interceptors.MockInterceptor
import com.ros.stjohnshhunt.data.ListResidentialResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RealtyService {

    @GET("properties/list-residential")
    suspend fun listResidential(
        @Query("LatitudeMax") latMax: String = "47.6340069",
        @Query("LongitudeMax") lngMax: String = "-52.6175349",
        @Query("LatitudeMin") latMin: String = "47.329816",
        @Query("LongitudeMin") lngMin: String = "-53.0378679",
        @Query("CurrentPage") curPage: String,
        @Query("RecordsPerPage") rcdPerPage: String = "50",
        @Query("NumberOfDays") numDays: String = "14", //Listed since
        @Query("BedRange") bedRange: String,
        @Query("BathRange") bathRange: String,
        @Query("PriceMin") minPrice: String,
        @Query("PriceMax") maxPrice: String,
        @Query("SortBy") sortBy: String = "6", //1-Price($)|6-Date|11-Virtual Tour|12-Open Houses|13-More Photos
        @Query("BuildingTypeId") buildingType: String = "1", //0-No Preference|1-House|2-Duplex|3-Triplex|5-Residential Commercial Mix|6-Mobile Home|12-Special Purpose|14-Other|16-Row / Townhouse|17-Apartment|19-Fourplex|20-Garden Home|26-Modular|27-Manufactured Home/Mobile|28-Commercial Apartment|29-Manufactured Home
        @Query("TransactionTypeId") transactionTypeId: String = "2", //2-For sale|3-For rent
        @Query("ConstructionStyleId") constructionStyle: String = "0", //0-No Preference|1-Attached|3-Detached|5-Semi-detached|7-Stacked|9-Link
        @Query("PropertySearchTypeId") propertySearchTypeId: String = "1" //0-No Preference|1-Residential|2-Recreational|3-Condo/Strata|4-Agriculture|5-Parking|6-Vacant Land|8-Multi Family

    //TODO: Move some static query params to interceptor
    ): ListResidentialResponse

    /*
    {
   "Location":"St. John's, NL, Canada", "Longitude":"-52.7125768", "Latitude":"47.5615096", "Height":0.3041909000000018"Width":0.4203329999999852"InternalResult":"False""Viewport":{
      "NorthEast":{
         "Latitude":"47.6340069""Longitude":"-52.6175349"
      }"SouthWest":{
         "Latitude":"47.329816""Longitude":"-53.0378679"
      }
   }

queryString <- list(
  LatitudeMax = "81.14747595814636",
  LatitudeMin = "-22.26872153207163",
  LongitudeMax = "-10.267941690981388",
  LongitudeMin = "-136.83037765324116",
  CurrentPage = "1",
  RecordsPerPage = "10",
  SortOrder = "A",
  SortBy = "1",
  CultureId = "1",
  NumberOfDays = "0",
  BedRange = "0-0",
  BathRange = "0-0"
  RentMin = "0",
)
     */

    companion object {
        private const val BASE_URL = "https://realty-in-ca1.p.rapidapi.com/"

        private const val RAPID_API_HOST = "realty-in-ca1.p.rapidapi.com"
        private const val RAPID_API_KEY = "fcd1fdf173msh5f407029ca16656p11e76cjsn3f89444a84fa"

        fun create(appContext: Context): RealtyService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder().apply {
                addInterceptor(logger)
                if (BuildConfig.DEBUG) {
                    addInterceptor(MockInterceptor(appContext))
                } else {
                    addInterceptor(
                        Interceptor { chain ->
                            val builder = chain.request().newBuilder()
                            builder.header("x-rapidapi-host", RAPID_API_HOST)
                            builder.header("x-rapidapi-key", RAPID_API_KEY)
                            return@Interceptor chain.proceed(builder.build())
                        }
                    )
                }
            }.build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RealtyService::class.java)
        }
    }

}