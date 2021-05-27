package ro.ubbcluj.dogsheltermanagement.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import ro.ubbcluj.dogsheltermanagement.Constants.DOG_AGE
import ro.ubbcluj.dogsheltermanagement.Constants.DOG_DESCRIPTION
import ro.ubbcluj.dogsheltermanagement.Constants.DOG_ID
import ro.ubbcluj.dogsheltermanagement.Constants.DOG_NAME
import ro.ubbcluj.dogsheltermanagement.Constants.DOG_RACE
import ro.ubbcluj.dogsheltermanagement.DogApplication
import ro.ubbcluj.dogsheltermanagement.DogListAdapter
import ro.ubbcluj.dogsheltermanagement.R
import ro.ubbcluj.dogsheltermanagement.model.Dog
import ro.ubbcluj.dogsheltermanagement.model.DogViewModel
import ro.ubbcluj.dogsheltermanagement.network.DogClient

class MainActivity : AppCompatActivity() {

    /*
    todo
    1. Create a new Dao
    2. Check for internet connectivity and if it's ok, then use that dao instead of the default database one.
    3. Else, use the in memory one.
    4. ???check when online to update the in memory one???

     */

    private val dogViewModel: DogViewModel by viewModels {
        DogViewModel.DogViewModelFactory((application as DogApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val recyclerView = findViewById<RecyclerView>(R.id.listDogs)
        val adapter = DogListAdapter(dogViewModel)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        dogViewModel.populateViewModelFromServer()

        dogViewModel.dogListData.observe(owner=this) { dogs ->
            dogs.let { adapter.submitList(it) }
        };

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val createButtonIntent = Intent(this, CreateActivity::class.java)
            startActivityForResult(createButtonIntent,1)
        }
    }

    private fun getNewDog(intent: Intent): Dog? {
        val extras: Bundle = intent.extras ?: return null
        if (!(extras.isEmpty)) {
            val newDogName = extras.getString(DOG_NAME)
            val newDogRace = extras.getString(DOG_RACE)
            val newDogDescription = extras.getString(DOG_DESCRIPTION)
            val newDogAge = extras.getString(DOG_AGE)
            if (newDogName.isNullOrBlank() or newDogRace.isNullOrBlank() or newDogDescription.isNullOrBlank() or newDogAge.isNullOrBlank()) {
                return null
            }
            if(extras.containsKey(DOG_ID)){
                return Dog(extras.getLong(DOG_ID),newDogName!!, newDogRace!!, newDogDescription!!, newDogAge!!.toInt())

            }
            return Dog(newDogName!!, newDogRace!!, newDogDescription!!, newDogAge!!.toInt())
        }
        return null;
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != 1) return
        if(resultCode == Activity.RESULT_OK){
            val newDog = data?.let { getNewDog(it) }
            newDog?.let {
                if(dogViewModel.dogListData.value?.contains(newDog) == true){
                    dogViewModel.update(newDog);
                }
                else {
                    dogViewModel.insert(newDog)
                }
            };
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }




}
