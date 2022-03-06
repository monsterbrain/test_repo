package com.faisal.employeedirectory

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.faisal.employeedirectory.api.ApiInterface
import com.faisal.employeedirectory.models.EmployeeModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.net.ConnectivityManager
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.faisal.employeedirectory.data.DataProvider
import com.faisal.employeedirectory.db.DatabaseClient
import com.faisal.employeedirectory.db.entity.AddressEntity
import com.faisal.employeedirectory.db.entity.CompanyEntity
import com.faisal.employeedirectory.db.entity.EmployeeEntity
import com.faisal.employeedirectory.utils.NetworkUtil.isOnline

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        if (isDataStoredInDB()) {
            // show details from DB
            showEmployeeListFromDB()
        } else {
            checkIfOnline {
                fetchEmployeeData()
            }
        }

    }

    private fun showEmployeeListFromDB() {

    }

    /**
     * Checks if Online and run the callback if online
     * @param callback Function0<Unit>
     */
    private fun checkIfOnline(callback: () -> Unit) {
        if (!isOnline()) {
            MaterialDialog(this).show {
                title(R.string.title_error)
                message(R.string.is_offline_error)
                positiveButton(R.string.action_retry) {
                    it.dismiss()
                    checkIfOnline(callback)
                }
            }
        } else {
            callback()
        }
    }

    private fun fetchEmployeeData() {
        val dataProvider = DataProvider()
        dataProvider.getEmployeeDataFromApi(this) { data, errMessage ->
            if (data != null) {
                saveToDatabase(data)
            } else {
                val errorMessage = getString(R.string.api_response_failure) + "[$errMessage]"
                MaterialDialog(this@MainActivity).show {
                    title(R.string.title_error)
                    message(null, errorMessage)
                    positiveButton(R.string.action_retry) {
                        it.dismiss()
                        fetchEmployeeData()
                    }
                }
            }
        }
    }

    private fun saveToDatabase(employeeList: List<EmployeeModel>) {
        var isDBWriteErrorOccurred = false
        try {
            employeeList.forEach {
                val employeeDao = DatabaseClient.instance().getEmployeeDao()

                val employeeEntity = EmployeeEntity(
                    0,
                    it.id,
                    it.name,
                    it.username,
                    it.email,
                    it.profileImage,
                    it.phone,
                    it.website
                )
                val employeeUid = employeeDao.insertEmployee(employeeEntity)

                it.address?.let { address ->
                    val addressEntity = AddressEntity(
                        0,
                        address.street,
                        address.suite,
                        address.city,
                        address.zipcode,
                        employeeUid?.toInt()
                        )
                    employeeDao.insertAddress(addressEntity)
                }

                it.company?.let { company ->
                    val companyEntity = CompanyEntity(
                        0,
                        company.name,
                        company.catchPhrase,
                        company.bs,
                        employeeUid?.toInt()
                    )
                    employeeDao.insertCompany(companyEntity)
                }
            }
        } catch (e: Exception) {
            MaterialDialog(this@MainActivity).show {
                title(R.string.title_error)
                message(null, "Failed to add to database error: ${e.message}")
                positiveButton(R.string.action_ok) {
                    it.dismiss()
                }
            }
            isDBWriteErrorOccurred = true
        }

        if (!isDBWriteErrorOccurred) {
            setIsDataStoredInDB(true)
        }
    }

    private fun isDataStoredInDB(): Boolean {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getBoolean(KEY_STORED_IN_DB, false)
    }

    private fun setIsDataStoredInDB(isStoredInDB: Boolean) {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        sharedPref.edit().putBoolean(KEY_STORED_IN_DB, isStoredInDB).apply()
    }


    companion object {
        const val TAG = "MainActivity"

        private const val KEY_STORED_IN_DB = "stored_in_db"
    }
}