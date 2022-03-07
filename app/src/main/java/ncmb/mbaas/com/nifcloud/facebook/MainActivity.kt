package ncmb.mbaas.com.nifcloud.facebook

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
        NCMB.initialize(this,
            "543e6ee053794c0ebbce6e668e4e86cf17a96dd2e841d3a99a6bc32576d314e0",
            "6c53e766837d00a8c4c7254c39c6536d1e1455aeb2dd30a0ee40ba0502375fba");

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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
