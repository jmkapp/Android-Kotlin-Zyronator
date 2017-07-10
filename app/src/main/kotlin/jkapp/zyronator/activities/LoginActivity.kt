package jkapp.zyronator.activities

import android.app.Activity
import android.app.FragmentTransaction
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import jkapp.zyronator.R
import jkapp.zyronator.api_calls.LoginApiCall
import jkapp.zyronator.api_calls.LoginApiCallback
import jkapp.zyronator.fragments.LoginFragmentCallback
import jkapp.zyronator.fragments.LoginFragment
import jkapp.zyronator.data.ListenerDisplay
import retrofit2.Call
import retrofit2.Response

class LoginActivity : AppCompatActivity(), LoginFragmentCallback, LoginApiCallback
{
    companion object
    {
        val LISTENER_TAG = "listener"
    }

    private val LOGIN_FRAGMENT_TAG = "loginFragment"

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if(savedInstanceState == null)
        {
            val loginFragment = LoginFragment()
            val transaction = fragmentManager.beginTransaction()
            transaction.add(R.id.login_activity, loginFragment, LOGIN_FRAGMENT_TAG)
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            transaction.commit()
        }
    }

    override fun loginFragmentCallback(userName: String, password: String)
    {
        startApiCall(userName, password)
    }

    private fun startApiCall(userName : String, password : String)
    {
        val apiCall = LoginApiCall(this, userName, password)
        apiCall.execute()
    }

    override fun LoginApiResponse(call: Call<ListenerDisplay>, response: Response<ListenerDisplay>)
    {
        val intent = Intent()

        if(response.isSuccessful && response.raw().code() == 200)
        {
            val listener = response.body()

            intent.putExtra(LISTENER_TAG, listener)
            setResult(Activity.RESULT_OK, intent)
        }
        else
        {
            setResult(Activity.RESULT_CANCELED, intent)
        }

        finish()
    }

    override fun LoginApiCallFailed(call: Call<ListenerDisplay>, t: Throwable)
    {
        Toast.makeText(applicationContext, "Find Listener API call failed: " + t.message, Toast.LENGTH_LONG).show()
    }
}
