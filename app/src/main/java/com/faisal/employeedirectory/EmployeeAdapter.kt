package com.faisal.employeedirectory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.faisal.employeedirectory.db.entity.EmployeeWithDataEntity

class EmployeeAdapter(private val employeeList: List<EmployeeWithDataEntity>) :
    RecyclerView.Adapter<EmployeeAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_layout_employee, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = employeeList[position]

        // sets the image to the imageview from our itemHolder class todo
        // holder.profileImageView.setImageResource(ItemsViewModel.image)

        // sets the text to the textview from our itemHolder class
        holder.textViewName.text = ItemsViewModel.employee.name
        holder.textViewCompanyName.text = ItemsViewModel.company?.name ?: "NA"

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return employeeList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val profileImageView: ImageView = itemView.findViewById(R.id.imageViewProfile)
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewCompanyName: TextView = itemView.findViewById(R.id.textViewCompany)
    }
}
