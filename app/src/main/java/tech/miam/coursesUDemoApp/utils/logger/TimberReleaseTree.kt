package com.example.androidDemoAppXml.utils.logger

import android.util.Log

import timber.log.Timber

/*
 * Created by Julien Cholin on 24/02/2023
 * Copyright © 2023 Miam. All rights reserved.
 */
class TimberReleaseTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        when(priority) {
            Log.ERROR -> {
                // should be sent with Sentry for example
            }
        }
    }
}