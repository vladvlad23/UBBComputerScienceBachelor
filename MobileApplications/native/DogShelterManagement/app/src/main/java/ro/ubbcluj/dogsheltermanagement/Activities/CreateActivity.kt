package ro.ubbcluj.dogsheltermanagement.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import ro.ubbcluj.dogsheltermanagement.Constants.DOG_AGE
import ro.ubbcluj.dogsheltermanagement.Constants.DOG_DESCRIPTION
import ro.ubbcluj.dogsheltermanagement.Constants.DOG_ID
import ro.ubbcluj.dogsheltermanagement.Constants.DOG_NAME
import ro.ubbcluj.dogsheltermanagement.Constants.DOG_RACE
import ro.ubbcluj.dogsheltermanagement.R

class CreateActivity : AppCompatActivity() {

    var dogId : Long = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        val extras: Bundle? = intent.extras
        if (extras != null) {
            if (!extras.isEmpty) {
                dogId = extras.getLong(DOG_ID);
                if(extras.containsKey(DOG_NAME)) {
                    val editDogName = extras.getString(DOG_NAME)
                    findViewById<EditText>(R.id.nameEditText).text = SpannableStringBuilder(editDogName)
                }
                if(extras.containsKey(DOG_RACE)){
                    val editDogRace = extras.getString(DOG_RACE)
                    findViewById<EditText>(R.id.raceEditText).text = SpannableStringBuilder(editDogRace)
                }
                if(extras.containsKey(DOG_DESCRIPTION)){
                    val editDogDescription = extras.getString(DOG_DESCRIPTION)
                    findViewById<EditText>(R.id.descriptionEditText).text = SpannableStringBuilder(editDogDescription)
                }
                if(extras.containsKey(DOG_AGE)){
                    val editDogAge = extras.getInt(DOG_AGE)
                    findViewById<EditText>(R.id.ageEditText).text = SpannableStringBuilder(editDogAge.toString())
                }

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_create,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.add_action -> {
            val nameEditText = findViewById<EditText>(R.id.nameEditText)
            val raceEditText = findViewById<EditText>(R.id.raceEditText)
            val descriptionEditText = findViewById<EditText>(R.id.descriptionEditText)
            val ageEditText = findViewById<EditText>(R.id.ageEditText)

            val mainIntent = Intent(this,MainActivity::class.java).apply {
                if(dogId != 0L){
                    putExtra(DOG_ID, dogId);
                }
                putExtra(DOG_NAME,nameEditText.text.toString())
                putExtra(DOG_RACE,raceEditText.text.toString())
                putExtra(DOG_DESCRIPTION,descriptionEditText.text.toString())
                putExtra(DOG_AGE,ageEditText.text.toString())
            }
            setResult(Activity.RESULT_OK,mainIntent)
            finish()
            true
        }
        else ->{
            super.onOptionsItemSelected(item);
        }
    }
}