package com.faisal.employeedirectory.screens

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import com.faisal.employeedirectory.R
import com.faisal.employeedirectory.databinding.ActivityProfileDetailsBinding
import com.faisal.employeedirectory.db.entity.EmployeeWithDataEntity
import com.squareup.picasso.Picasso

class ProfileDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Details"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val employeeWithDataEntity =
            intent.getSerializableExtra(PARAM_EMPLOYEE_DATA) as? EmployeeWithDataEntity ?: null

        employeeWithDataEntity?.let {
            with(binding) {
                Picasso.get().load(it.employee.profileImage).placeholder(R.drawable.ic_placeholder).into(imageViewProfile)

                textViewName.text = it.employee.name
                textViewUserName.text = "username: " + it.employee.username
                textViewEmail.text = it.employee.email
                textViewPhone.text = "📞" + it.employee.phone
                textViewWebsite.text = "🌐" + (it.employee.website ?: "NA")

                // todo check order of address
                textViewAddress.text = it.address.suite+", "+it.address.street+", "+it.address.city+", "+it.address.zipcode

                it.company?.let { companyEntity ->
                    textViewCompanyName.text = companyEntity.name
                    textViewCompanyCatchPhrase.text = companyEntity.catchPhrase

                } ?: kotlin.run {
                    textViewCompanyName.text = "NA"
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val PARAM_EMPLOYEE_DATA = "param_employee_data"
        fun start(context: Context, employeeWithDataEntity: EmployeeWithDataEntity) {
            val intent = Intent(context, ProfileDetailsActivity::class.java)
            intent.putExtra(PARAM_EMPLOYEE_DATA, employeeWithDataEntity)
            context.startActivity(intent)
        }
    }
}