package com.example.cloudkitchens.utils

import android.util.Log

class AndroidLogger : Logger {
    override fun e(tag: String, message: String) {
        Log.e(tag, message)
    }
}
