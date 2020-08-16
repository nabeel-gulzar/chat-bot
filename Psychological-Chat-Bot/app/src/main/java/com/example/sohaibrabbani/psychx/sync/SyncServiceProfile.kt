package com.example.sohaibrabbani.psychx.sync

import android.app.Service
import android.content.Intent
import android.os.IBinder

class SyncServiceProfile : Service() {

    private val sSyncAdapterLock = Any()
    private var sSyncAdapter: SyncAdapterProfile? = null
    /*
 * Instantiate the sync adapter object.
 */
    override fun onCreate() {
        super.onCreate()
        /*
     * Create the sync adapter as a singleton.
     * Set the sync adapter as syncable
     * Disallow parallel syncs
     */
        synchronized(sSyncAdapterLock) {
            if (sSyncAdapter == null) {
                sSyncAdapter = SyncAdapterProfile(applicationContext, true)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
    override fun onBind(intent: Intent): IBinder? {
        /*
     * Get the object that allows external processes
     * to call onPerformSync(). The object is created
     * in the base class code when the SyncAdapter
     * constructors call super()
     */
        return sSyncAdapter!!.getSyncAdapterBinder()
    }
}
