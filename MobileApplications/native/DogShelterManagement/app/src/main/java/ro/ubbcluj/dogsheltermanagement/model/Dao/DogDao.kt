package ro.ubbcluj.dogsheltermanagement.model.Dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ro.ubbcluj.dogsheltermanagement.model.Dog

@Dao
interface DogDao {

    @Query("SELECT * FROM dog")
    fun getAll(): Flow<List<Dog>>

    @Insert
    suspend fun insertDog(dog: Dog)

    @Insert
    suspend fun insertAllDogs(dogs: List<Dog>)

    @Update
    suspend fun updateAllDogs(dogs: List<Dog>)

    @Update
    suspend fun updateDog(dog: Dog)

    @Delete
    suspend fun delete(dog: Dog)

    @Query("DELETE FROM dog")
    suspend fun deleteAll()

}