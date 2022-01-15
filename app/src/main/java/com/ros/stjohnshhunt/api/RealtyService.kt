package com.ros.stjohnshhunt.api

import com.ros.stjohnshhunt.data.ListResidentialResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
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
        @Query("NumberOfDays") numDays: String = "0",
        @Query("BedRange") bedRange: String,
        @Query("BathRange") bathRange: String,
        @Query("PriceMin") minPrice: String,
        @Query("PriceMax") maxPrice: String
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

        fun create(): RealtyService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .addInterceptor(
                    Interceptor { chain ->
                        val builder = chain.request().newBuilder()
                        builder.header("x-rapidapi-host", RAPID_API_HOST)
                        builder.header("x-rapidapi-key", RAPID_API_KEY)
                        return@Interceptor chain.proceed(builder.build())
                    }
                )
                .build()


            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RealtyService::class.java)
        }
    }

}