package ro.ubbcluj.dogsheltermanagement.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Dog(
    @PrimaryKey(autoGenerate = false) val id: Long?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "race") val race: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "age") val age: Int
){
    constructor(name: String, race: String, description: String, age: Int) : this(null,name,race,description,age)

    override fun equals(other: Any?): Boolean {
        if(other is Dog){
            return other.id == this.id
        }
        return false;
    }
}