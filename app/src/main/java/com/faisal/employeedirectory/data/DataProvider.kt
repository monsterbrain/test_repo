package com.faisal.employeedirectory.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.faisal.employeedirectory.screens.MainActivity
import com.faisal.employeedirectory.api.ApiInterface
import com.faisal.employeedirectory.models.EmployeeModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataProvider() {
    fun getEmployeeDataFromApi(
        context: Context,
        callback: (data: List<EmployeeModel>?, errorMsg: String?) -> Unit
    ) {
        val employeeApiCall = ApiInterface.create().getEmployeeList()
        employeeApiCall.enqueue(object : Callback<List<EmployeeModel>> {
            override fun onResponse(
                call: Call<List<EmployeeModel>>,
                response: Response<List<EmployeeModel>>
            ) {
                Log.i(MainActivity.TAG, "onResponse: ${response.body()}")
                val employeeList = response.body()
                employeeList?.let {
                    callback(it, null)
                } ?: kotlin.run {
                    Toast.makeText(
                        context,
                        "Empty Employee list returned or Server Error.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<EmployeeModel>>, t: Throwable) {
                callback(null, t.message)
            }
        })
    }

    fun getEmployeeDataFromDB() {

    }
}