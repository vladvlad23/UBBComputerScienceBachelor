package ro.ubbcluj.dogsheltermanagement.network

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import ro.ubbcluj.dogsheltermanagement.model.Dog

interface DogClient {

    @GET("/dogs")
    fun allDogs(): Call<List<Dog>>

    @POST("/dogs")
    fun addDog(@Body dog:Dog) : Call<Dog>

    @DELETE("/dogs/{dogId}")
    fun deleteDog(@Path("dogId") dogId: Long): Call<ResponseBody>

    @PUT("/dogs")
    fun updateDog(@Body dog: Dog) : Call<ResponseBody>

}