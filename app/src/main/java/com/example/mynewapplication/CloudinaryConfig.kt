package com.example.mynewapplication

import android.content.Context
import com.cloudinary.android.MediaManager

object CloudinaryConfig {

    fun init(context: Context) {

        val config = HashMap<String, String>()

        config["cloud_name"] = "djqzkvaj3"
        config["api_key"] = "579745837991721"
        config["api_secret"] = "DdjGGT-cCPbYllghSEMW8dqrLFY"

        MediaManager.init(context, config)
    }
}