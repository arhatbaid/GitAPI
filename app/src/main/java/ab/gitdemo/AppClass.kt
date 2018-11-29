package ab.gitdemo

import android.app.Application
import android.content.ContextWrapper
import android.support.multidex.MultiDex
import com.pixplicity.easyprefs.library.Prefs

class AppClass : Application() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)

        // Initialize the Prefs class
        Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(packageName)
                .setUseDefaultSharedPreference(true)
                .build()
    }
}