package jkapp.zyronator.activities

import android.app.Activity
import android.app.FragmentTransaction
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.Toast
import jkapp.zyronator.fragments.CurrentMixTextPressed
import jkapp.zyronator.fragments.MixButtonPressed
import jkapp.zyronator.R
import jkapp.zyronator.fragments.LastListenedScreenFragment
import jkapp.zyronator.api_calls.CreateListenerMixActivityCallback
import jkapp.zyronator.api_calls.CreateListenerMixApiCall
import jkapp.zyronator.api_calls.GetListenerMixByUrlApiCallback
import jkapp.zyronator.data.*
import retrofit2.Call
import retrofit2.Response

internal class MainActivity : AppCompatActivity(),
        MixButtonPressed,
        CreateListenerMixActivityCallback,
        CurrentMixTextPressed,
        ListenerMixDetails,
        GetListenerMixByUrlApiCallback
{
    internal val LAST_LISTENED_FRAGMENT = "lastListenedFragment"
    private val LOGIN = 21
    internal val CURRENT_LISTENER_MIX = "currentListenerMix"
    internal val NEXT_LISTENER_MIX = "nextListenerMix"
    private val LOGGED_IN_LISTENER = "loggedInListener"

    private val FIND_MIX = 2

    private lateinit var _loggedInListener: ListenerDisplay
    internal lateinit var currentListenerMix: ListenerMixDisplay
    internal lateinit var nextListenerMix: ListenerMixDisplay

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null)
        {
            val lastListenedFragment = LastListenedScreenFragment()
            val transaction = fragmentManager.beginTransaction()
            transaction.add(R.id.main_activity, lastListenedFragment, LAST_LISTENED_FRAGMENT)
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            transaction.commit()

            val intent = Intent(this, LoginActivity::class.java)
            startActivityForResult(intent, LOGIN)

            _loggedInListener = ListenerDisplay("", null)
            currentListenerMix = ListenerMixDisplay()
            nextListenerMix = ListenerMixDisplay()
        }
        else
        {
            _loggedInListener = savedInstanceState.getParcelable(LOGGED_IN_LISTENER)
            currentListenerMix = savedInstanceState.getParcelable(CURRENT_LISTENER_MIX)
            nextListenerMix = savedInstanceState.getParcelable(NEXT_LISTENER_MIX)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)

        if(data != null)
        {
            if(requestCode == LOGIN)
            {
                if(resultCode == Activity.RESULT_OK)
                {
                    _loggedInListener = data.getParcelableExtra<ListenerDisplay>(LoginActivity.LISTENER_TAG)

                    setLastListenedMixes()
                }
                else
                {
                    Toast.makeText(applicationContext, "Incorrect username or password", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivityForResult(intent, LOGIN)
                }
            }
            else if(requestCode == FIND_MIX)
            {
                if(resultCode == Activity.RESULT_OK)
                {
                    val mix = data.getParcelableExtra<Mix>(FindMixesActivity.MIX_RESULT)
                    val apiCall = CreateListenerMixApiCall(activityCallback = this, listenerUrl = _loggedInListener._links?.self?.href ?: "", mixUrl = mix._links.self.href)
                    apiCall.execute()
                }
            }
        }
    }

    override fun CreateListenerMixApiResponse(call: Call<ListenerMixDisplay>, response: Response<ListenerMixDisplay>)
    {
        if(response.isSuccessful)
        {
            setNextMix(response.body())
        }
        else
        {
            Toast.makeText(applicationContext, "CreateListenerMix API call failed: " + response.message(), Toast.LENGTH_LONG).show()
        }
    }

    override fun CreateListenerMixApiCallFailed(call : Call<ListenerMixDisplay>, t : Throwable)
    {
        Toast.makeText(applicationContext, "CreateListenerMix API call failed: " + t.message, Toast.LENGTH_LONG).show()
    }

    private fun setLastListenedMixes()
    {
        val lastListenedMixesGetter = LastListenedMixesGetter(_activity = this, _lastListenedUrl = _loggedInListener._links?.lastListened?.href ?: "")
        lastListenedMixesGetter.execute()
    }

    private fun setNextMix(listenerMixDisplay : ListenerMixDisplay)
    {
        nextListenerMix = listenerMixDisplay

        val fragment = fragmentManager.findFragmentByTag(LAST_LISTENED_FRAGMENT) as LastListenedScreenFragment
        fragment.setNextMix(nextListenerMix.mixTitle, nextListenerMix.lastListenedDate)

        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(NEXT_LISTENER_MIX, nextListenerMix._links!!.self.href)
        editor.apply()
    }

    override fun getListenerMixByUrlApiResponse(listenerMix: ListenerMixDisplayOld, tag: String)
    {
//        if(tag == CURRENT_LISTENER_MIX)
//        {
//            currentListenerMix = listenerMix
//
//            val fragment = fragmentManager.findFragmentByTag(LAST_LISTENED_FRAGMENT) as LastListenedScreenFragment
//            fragment.setCurrentMix(currentListenerMix.mixTitle, currentListenerMix.lastListenedDate)
//        }
//        else
//        {
//            nextListenerMix = listenerMix
//
//            val fragment = fragmentManager.findFragmentByTag(LAST_LISTENED_FRAGMENT) as LastListenedScreenFragment
//            fragment.setNextMix(nextListenerMix.mixTitle, nextListenerMix.lastListenedDate)
//        }
    }

    override fun getListenerMixByUrlApiCallFailed(call: Call<ListenerMix>, t: Throwable, tag: String)
    {
        Toast.makeText(applicationContext, "GetListenerMixByUrl API call failed: " + t.message, Toast.LENGTH_LONG).show()
    }

    override fun getCurrentMixName() : String
    {
        return currentListenerMix.mixTitle
    }

    override fun getCurrentMixDate() : String
    {
        return currentListenerMix.lastListenedDate
    }

    override fun getNextMixName() : String
    {
        return nextListenerMix.mixTitle
    }

    override fun getNextMixDate() : String
    {
        return nextListenerMix.lastListenedDate
    }

    override fun startNextMixButtonPressed()
    {
        val nextListenerMixUrl = nextListenerMix._links?.self?.href ?: ""

        val currentMixSetter = CurrentMixUpdater(_activity = this, _currentListenerMixUrl = nextListenerMixUrl, _comment = nextListenerMix.listenerMixComment)
        currentMixSetter.update()
    }

    override fun findMixButtonPressed()
    {
        val intent = Intent(this, FindMixesActivity::class.java)
        intent.putExtra(FindMixesActivity.LISTENER_URL, _loggedInListener._links?.self?.href ?: "")
        startActivityForResult(intent, FIND_MIX)
    }

    override fun currentMixTextPressed()
    {
        if(currentListenerMix.discogsApiUrl.isNullOrBlank())
        {
//            val discogsUserListsIntent = Intent(this, UpdateDiscogsUserListApiUrlActivity::class.java)
//            discogsUserListsIntent.putExtra(UpdateDiscogsUserListApiUrlActivity.LISTENER_MIX_URL, lastListenedMixes.currentListenerMixSelf)
//            startActivityForResult(discogsUserListsIntent, UPDATE_DISCOGS_API_URL)
        }
        else
        {
            displayDiscogsUserListItems()
        }
    }

    private fun displayDiscogsUserListItems()
    {
        val discogsApiUrl = currentListenerMix.discogsApiUrl

        val listDetailsIntent = Intent(this, DisplayDiscogsUserListItemsActivity::class.java)
        listDetailsIntent.putExtra(DisplayDiscogsUserListItemsActivity.LIST_API_URL_EXTRA, discogsApiUrl)
        startActivity(listDetailsIntent)
    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        outState.putParcelable(CURRENT_LISTENER_MIX, currentListenerMix)
        outState.putParcelable(NEXT_LISTENER_MIX, nextListenerMix)
        outState.putParcelable(LOGGED_IN_LISTENER, _loggedInListener)

        super.onSaveInstanceState(outState)
    }
}