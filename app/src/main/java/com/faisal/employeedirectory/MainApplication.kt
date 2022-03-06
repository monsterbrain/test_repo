package com.faisal.employeedirectory

import android.app.Application
import com.faisal.employeedirectory.db.DatabaseClient

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        DatabaseClient.init(this)
    }
}