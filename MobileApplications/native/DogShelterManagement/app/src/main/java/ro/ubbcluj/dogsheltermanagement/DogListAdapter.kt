package ro.ubbcluj.dogsheltermanagement

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ro.ubbcluj.dogsheltermanagement.Activities.CreateActivity
import ro.ubbcluj.dogsheltermanagement.model.Dog
import ro.ubbcluj.dogsheltermanagement.model.DogViewModel


class DogListAdapter(private val dogViewModel: DogViewModel) :
    ListAdapter<Dog, DogListAdapter.DogViewHolder>(DOGS_COMPARATOR) {

    class DogViewHolder(
        rowRecyclerView: View
    ) :
        RecyclerView.ViewHolder(rowRecyclerView) {
        val dogName: TextView = rowRecyclerView.findViewById(R.id.dogNameTextView)

        fun bind(text: String) {
            dogName.text = text
        }

        companion object {
            fun create(parent: ViewGroup): DogViewHolder {
                val rowRecyclerView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_row, parent, false)
                return DogViewHolder(rowRecyclerView);
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        return DogViewHolder.create(parent);
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.bind(getItem(position).name)
        holder.itemView.setOnClickListener { onClick(holder.itemView, holder) }
        holder.itemView.setOnLongClickListener { onLongClick(holder.itemView, holder) }
    }

    companion object {
        private val DOGS_COMPARATOR = object : DiffUtil.ItemCallback<Dog>() {
            override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }


    fun onLongClick(view: View?, viewHolder: DogViewHolder): Boolean {
        if (view != null) {
            val builder = AlertDialog.Builder(view.context)
            val options = arrayOf(
                view.context?.getString(R.string.editDogOption),
                view.context?.getString(R.string.deleteDogOption)
            )
            val checkedOption = 1
            builder.setTitle(view.context?.getString(R.string.editOrDeleteTitle))
            builder.setSingleChoiceItems(options, checkedOption, null)
            builder.setPositiveButton(
                R.string.postive_action,
                DialogInterfaceHandler(view.context, viewHolder)
            )
            builder.show()
            return true
        }
        return false
    }


    fun onClick(view: View?, viewHolder: DogViewHolder) {
        val alertDialog =
            AlertDialog.Builder(view?.context).create()
        alertDialog.setTitle(R.string.dogDetailsTitle)
        val dogNameLabel: String? = view?.context?.getString(R.string.dogNameLabel)
        val dogDescriptionLabel: String? = view?.context?.getString(R.string.dogDescriptionLabel)
        val dogRaceLabel: String? = view?.context?.getString(R.string.dogRaceLabel)
        val dogAgeLabel: String? = view?.context?.getString(R.string.dogAgeLabel)

        alertDialog.setMessage(
            dogNameLabel?.plus(getItem(viewHolder.adapterPosition).name)
                .plus("\n")
                .plus(dogDescriptionLabel)
                .plus(getItem(viewHolder.adapterPosition).description)
                .plus("\n")
                .plus(dogRaceLabel).plus(getItem(viewHolder.adapterPosition).race)
                .plus("\n")
                .plus(dogAgeLabel).plus(getItem(viewHolder.adapterPosition).age)
        )
        alertDialog.setButton(
            AlertDialog.BUTTON_NEUTRAL, "OK"
        ) { dialog, _ -> dialog.dismiss() }
        alertDialog.show()
    }




    inner class DialogInterfaceHandler(
        private val context: Context,
        private val viewHolder: DogViewHolder
    ) : DialogInterface.OnClickListener {

        override fun onClick(dialog: DialogInterface?, which: Int) {
            if (dialog is AlertDialog) {
                when (dialog.listView.checkedItemPosition) {
                    0 -> {
                        val editDogIntent = Intent(context, CreateActivity::class.java).apply {
                            val currentDog = getItem(viewHolder.adapterPosition)
                            putExtra(Constants.DOG_ID, currentDog.id)
                            putExtra(Constants.DOG_NAME, currentDog.name)
                            putExtra(Constants.DOG_RACE, currentDog.race)
                            putExtra(Constants.DOG_DESCRIPTION, currentDog.description)
                            putExtra(Constants.DOG_AGE, currentDog.age)
                        }
                        (context as Activity).startActivityForResult(editDogIntent, 1)
                    }
                    1 -> {
                        val dogToRemove = getItem(viewHolder.adapterPosition)
                        val dogList = currentList.filter { dog -> dog != dogToRemove }
                        dogViewModel.delete(dogToRemove)
                        submitList(dogList)
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }


}