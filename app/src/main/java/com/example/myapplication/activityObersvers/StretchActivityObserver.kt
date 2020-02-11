package com.example.myapplication.activityObersvers

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class StretchActivityObserver : LifecycleObserver {

    private val TAG = javaClass.simpleName

    //testing

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public fun onCreateEvent() {
        Log.i(TAG, "Observer ON_CREATE")

    }

}