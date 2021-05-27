package ro.ubbcluj.dogsheltermanagement

import android.app.Application
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ro.ubbcluj.dogsheltermanagement.database.DogRoomDatabase
import ro.ubbcluj.dogsheltermanagement.network.DogClient
import ro.ubbcluj.dogsheltermanagement.repository.DogRepository

class DogApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { DogRoomDatabase.getDatabase(this,applicationScope)}
    val repository by lazy {

        DogRepository(database.dogDao(),getClient(),this)
    }

    private fun getClient(): DogClient {
        val builder = Retrofit.Builder()
            .baseUrl("http://192.168.0.185:8080")
            .addConverterFactory(GsonConverterFactory.create())

        val retrofit = builder.build()

        return retrofit.create(
            DogClient::class.java)

    }
}