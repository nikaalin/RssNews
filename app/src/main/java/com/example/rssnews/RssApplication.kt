package com.example.rssnews

import android.app.Application
import android.content.Context

class RssApplication: Application() {

    companion object {
        private var context: Context? = null
        fun getAppContext(): Context? {
            return context
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}