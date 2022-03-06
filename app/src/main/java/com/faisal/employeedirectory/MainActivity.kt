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

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i(TAG, "onCreate: ${isNetworkConnected()}")

        val employeeApiCall = ApiInterface.create().getEmployeeList()
        employeeApiCall.enqueue(object : Callback<List<EmployeeModel>>{
            override fun onResponse(
                call: Call<List<EmployeeModel>>,
                response: Response<List<EmployeeModel>>
            ) {
                Log.i(TAG, "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<List<EmployeeModel>>, t: Throwable) {
                Log.i(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun isNetworkConnected(): Boolean {
        val connMgr = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        if (connMgr != null) {
            val activeNetworkInfo = connMgr.activeNetworkInfo
            if (activeNetworkInfo != null) { // connected to the internet
                // connected to the mobile provider's data plan
                return if (activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    true
                } else activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE
            }
        }
        return false
    }

    companion object {
        const val TAG = "MainActivity"
    }
}