package com.comst.planding

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@HiltAndroidApp
class App : Application(){

    override fun onCreate() {
        super.onCreate()
        dummyCoroutines()
    }
    @OptIn(DelicateCoroutinesApi::class)
    private fun dummyCoroutines(){
        GlobalScope.launch(Dispatchers.Default) {

            GlobalScope.launch(Dispatchers.IO) {}

            GlobalScope.launch(Dispatchers.Main) {}

            GlobalScope.launch(Dispatchers.Unconfined) {}

        }
    }
}