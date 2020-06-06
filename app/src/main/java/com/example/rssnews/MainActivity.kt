package com.example.rssnews

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.rssnews.repo.Service
import com.example.rssnews.ui.main.MainFragment
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        val retrofit = Service.create()
        val parentJob = Job()

        val coroutineContext: CoroutineContext = parentJob + Dispatchers.Default

        val scope = CoroutineScope(coroutineContext)

        scope.launch {
            val result =
                withContext(Dispatchers.Default) {
                    retrofit.getFeed().execute()
                }
            Log.d(
                "DATA",
                if (result.isSuccessful)
                    result.body()?.channel?.title ?: "NULL"
                else result.code().toString()
            )
        }


    }
}
