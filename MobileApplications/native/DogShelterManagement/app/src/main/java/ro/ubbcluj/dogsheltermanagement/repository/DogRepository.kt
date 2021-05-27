package ro.ubbcluj.dogsheltermanagement.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse
import ro.ubbcluj.dogsheltermanagement.model.Dao.DogDao
import ro.ubbcluj.dogsheltermanagement.model.Dog
import ro.ubbcluj.dogsheltermanagement.network.DogClient

class DogRepository(
    private val dogDao: DogDao,
    private val dogClient: DogClient,
    private val context: Context
) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun initializeFromServer(){
        dogDao.deleteAll()
        isOnline(context,true)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun isOnline(context: Context,initialization: Boolean): Boolean {
        var returnValue = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    returnValue = true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    returnValue = true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    returnValue = true
                }
            }
        }
        var dogsToAdd: List<Dog>? = null
        if (returnValue) {
            val result = dogClient.allDogs().awaitResponse()
            if(result.isSuccessful){
                Log.d("GETTING DATA FROM SERVER", "SUCCESFUL")
                dogsToAdd = result.body()
            }
        }
        insertAll(dogsToAdd,initialization)
        return returnValue
    }

    val allDogs: Flow<List<Dog>> = dogDao.getAll();

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(dog: Dog) {
        if (isOnline(context,false)) {
            val call = dogClient.addDog(dog)
            val resultDog = call.awaitResponse().body();
            if (resultDog != null) {
                dogDao.insertDog(resultDog);
            }
        } else {
            dogDao.insertDog(dog);
        }
//        else{
//            log boom
//        }

    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(dog: Dog) {
        if (isOnline(context,false)) {
            val call = dogClient.updateDog(dog)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        Log.d("DATABASE OPERATION", "Success updated to server")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("DATABASE OPERATION", "Failed to update to server");
                }
            })
            dogDao.updateDog(dog)
        }
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(dog: Dog) {
        dogDao.delete(dog);
        if (dog.id != null) {
            if (isOnline(context,false)) {
                var call = dogClient.deleteDog(dog.id)
                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {
                            Log.d("DATABASE OPERATION", "Success deleted from server")
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d("DATABASE OPERATION", "Failed to delete from server");
                    }

                })
            }
            dogDao.delete(dog)
        }
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAll(result: List<Dog>?, initialization: Boolean) {
        if (null != result) {
            if(initialization == true) {
                dogDao.insertAllDogs(result)
            }
            else{
                dogDao.updateAllDogs(result)
            }
        }
    }






}
