package ncmb.mbaas.com.nifcloud.facebook

import android.app.Application

import com.facebook.appevents.AppEventsLogger

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Facebook settings
        AppEventsLogger.activateApp(this)
    }
}