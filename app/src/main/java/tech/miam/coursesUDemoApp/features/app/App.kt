package tech.miam.coursesUDemoApp.features.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ProcessLifecycleOwner
import org.koin.core.component.KoinComponent
import timber.log.Timber

open class App :Application(), KoinComponent{
    private val lifecycleEventObserver = LifecycleEventObserver { source, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> Timber.tag(TAG).d("App in foreground")
            Lifecycle.Event.ON_PAUSE -> Timber.tag(TAG).d("App in background")
            Lifecycle.Event.ON_DESTROY -> Timber.tag(TAG).d("👋 App destroyed!")
            Lifecycle.Event.ON_CREATE -> {}
            Lifecycle.Event.ON_START -> {}
            Lifecycle.Event.ON_STOP -> {}
            Lifecycle.Event.ON_ANY -> {}
        }
    }

    override fun onCreate() {
        super.onCreate()

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityDestroyed(activity: Activity) {
                Timber.tag(TAG).d("🟠 onActivityDestroyed > ${activity::class.java.simpleName}")
            }
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                Timber.tag(TAG).d("🟢 onActivityCreated > ${activity::class.java.simpleName}")
            }
        })

        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleEventObserver)
        with(ProcessLifecycleOwner.get()) {
//            lifecycleScope.launch() {}
        }
    }

    companion object {
        const val TAG = "App"
    }
}