package com.faisal.employeedirectory.api

import com.faisal.employeedirectory.models.EmployeeModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {

    @GET("http://www.mocky.io/v2/5d565297300000680030a986")
    fun getEmployeeList() : Call<List<EmployeeModel>>

    companion object {
        private const val BASE_URL = "http://www.mocky.io/v2/"

        fun create(): ApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }
}