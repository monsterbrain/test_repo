package com.faisal.employeedirectory

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
import com.faisal.employeedirectory.db.DatabaseClient
import com.faisal.employeedirectory.db.entity.AddressEntity
import com.faisal.employeedirectory.db.entity.EmployeeEntity
import com.faisal.employeedirectory.utils.NetworkUtil.isOnline

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        checkIfOnline {
            fetchEmployeeData(true)
        }
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

    private fun fetchEmployeeData(fromServer: Boolean) {
        val employeeApiCall = ApiInterface.create().getEmployeeList()
        employeeApiCall.enqueue(object : Callback<List<EmployeeModel>>{
            override fun onResponse(
                call: Call<List<EmployeeModel>>,
                response: Response<List<EmployeeModel>>
            ) {
                Log.i(TAG, "onResponse: ${response.body()}")
                val employeeList = response.body()
                employeeList?.let {
                    saveToDatabase(it)
                } ?: kotlin.run {
                    Toast.makeText(
                        this@MainActivity,
                        "Empty Employee list returned or Server Error.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<EmployeeModel>>, t: Throwable) {
                runOnUiThread {
                    val errorMessage = getString(R.string.api_response_failure) + "[${t.message}]"
                    MaterialDialog(this@MainActivity).show {
                        title(R.string.title_error)
                        message(null, errorMessage)
                        positiveButton(R.string.action_retry) {
                            it.dismiss()
                            fetchEmployeeData(fromServer)
                        }
                    }
                }
            }

        })
    }

    private fun saveToDatabase(employeeList: List<EmployeeModel>) {
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
            employeeDao.insertEmployee(employeeEntity)

            it.address?.let { address ->
                val addressEntity = AddressEntity(
                    0,
                    address.street,
                    address.suite,
                    address.city,
                    address.zipcode,
                    it.id
                    )
                employeeDao.insertAddress(addressEntity)
            } ?: kotlin.run {
                // todo
            }
        }

        DatabaseClient.instance().getEmployeeDao().getAll()
    }


    companion object {
        const val TAG = "MainActivity"
    }
}