package ro.ubbcluj.dogsheltermanagement.model

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ro.ubbcluj.dogsheltermanagement.repository.DogRepository
import java.lang.IllegalArgumentException

class DogViewModel(private val repository: DogRepository) : ViewModel() {

    val dogListData: LiveData<List<Dog>> = repository.allDogs.asLiveData();

    fun insert(dog: Dog) = viewModelScope.launch {
        repository.insert(dog)
    }

    fun update(dog: Dog) = viewModelScope.launch {
        repository.update(dog)
    }

    fun delete(dog: Dog) = viewModelScope.launch {
        repository.delete(dog)
    }

    fun populateViewModelFromServer() = viewModelScope.launch {
        repository.initializeFromServer()
    }

    class DogViewModelFactory(private val repository: DogRepository) : ViewModelProvider.Factory {
        override fun <T: ViewModel> create(modelClass: Class<T>) : T {
            if(modelClass.isAssignableFrom(DogViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DogViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}

