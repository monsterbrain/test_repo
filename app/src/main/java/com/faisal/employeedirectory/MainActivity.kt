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
import com.afollestad.materialdialogs.MaterialDialog
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


    companion object {
        const val TAG = "MainActivity"
    }
}