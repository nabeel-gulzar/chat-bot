package com.example.sohaibrabbani.psychx.sync

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.NetworkErrorException
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.example.sohaibrabbani.psychx.R

/**
 * Created by sohaibrabbani on 1/3/2018.
 */
/**
 * A bound Service that instantiates the authenticator
 * when started.
 */
public class AuthenticatorService : Service() {
    private val TAG = "GenericAccountService"
    private val ACCOUNT_TYPE = getString(R.string.sync_account_type)
    val ACCOUNT_NAME = "PsychX"
    // Instance field that stores the authenticator object
    private lateinit var mAuthenticator: Authenticator

    override fun onCreate() {
        // Create a new authenticator object
        Log.i(TAG, "Service created")
        mAuthenticator = Authenticator(this);
    }

    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    override fun onDestroy() {

        Log.i(TAG, "Service destroyed")
    }
    override fun onBind(p0: Intent?): IBinder {

        return mAuthenticator.getIBinder();
    }

    class Authenticator(context: Context) : AbstractAccountAuthenticator(context) {

        // Editing properties is not supported

        override fun editProperties(r: AccountAuthenticatorResponse, s: String): Bundle {
            throw UnsupportedOperationException()
        }

        // Don't add additional accounts
        @Throws(NetworkErrorException::class)
        override fun addAccount(
                r: AccountAuthenticatorResponse,
                s: String,
                s2: String,
                strings: Array<String>,
                bundle: Bundle): Bundle? {
            return null
        }

        // Ignore attempts to confirm credentials
        @Throws(NetworkErrorException::class)
        override fun confirmCredentials(
                r: AccountAuthenticatorResponse,
                account: Account,
                bundle: Bundle): Bundle? {
            return null
        }

        // Getting an authentication token is not supported
        @Throws(NetworkErrorException::class)
        override fun getAuthToken(
                r: AccountAuthenticatorResponse,
                account: Account,
                s: String,
                bundle: Bundle): Bundle {
            throw UnsupportedOperationException()
        }

        // Getting a label for the auth token is not supported
        override fun getAuthTokenLabel(s: String): String {
            throw UnsupportedOperationException()
        }

        // Updating user credentials is not supported
        @Throws(NetworkErrorException::class)
        override fun updateCredentials(
                r: AccountAuthenticatorResponse,
                account: Account,
                s: String, bundle: Bundle): Bundle {
            throw UnsupportedOperationException()
        }

        // Checking features for the account is not supported
        @Throws(NetworkErrorException::class)
        override fun hasFeatures(
                r: AccountAuthenticatorResponse,
                account: Account, strings: Array<String>): Bundle {
            throw UnsupportedOperationException()
        }
    }
}
