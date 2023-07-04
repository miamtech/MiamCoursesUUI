package com.example.androidDemoAppXml.utils.logger

import android.util.Log

import timber.log.Timber

/*
 * Created by Julien Cholin on 24/02/2023
 * Copyright © 2023 Miam. All rights reserved.
 */
class TimberDebugTree : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        super.log(priority, tag, message, t)

        when(priority) {
            Log.ERROR -> {
                // should be sent with Sentry for example
            }
        }
    }
}