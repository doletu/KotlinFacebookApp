package ncmb.mbaas.com.nifcloud.facebook

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.nifcloud.mbaas.core.NCMB
import com.nifcloud.mbaas.core.NCMBException
import com.nifcloud.mbaas.core.NCMBFacebookParameters
import com.nifcloud.mbaas.core.NCMBUser


class MainActivity : AppCompatActivity() {

    internal lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //**************** APIキーの設定とSDKの初期化 **********************
        NCMB.initialize(this, "YOUR_APPLICATION_KEY", "YOUR_CLIENT_KEY");

        setContentView(R.layout.activity_main)

        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {

                        //Login to NIFCLOUD mobile backend
                        val parameters = NCMBFacebookParameters(
                                loginResult.accessToken.userId,
                                loginResult.accessToken.token,
                                loginResult.accessToken.expires
                        )
                        try {
                            NCMBUser.loginWith(parameters)
                            Toast.makeText(applicationContext, "Login to NIFCLOUD mbaas with Facebook account", Toast.LENGTH_LONG).show()
                        } catch (e: NCMBException) {
                            e.printStackTrace()
                        }

                    }

                    override fun onCancel() {
                        // App code
                        Log.d("tag", "onCancel")
                    }

                    override fun onError(exception: FacebookException) {
                        // App code
                        Log.d("tag", "onError:$exception")
                    }
                })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

}
