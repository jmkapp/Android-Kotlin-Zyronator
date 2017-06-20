package jkapp.zyronator

import android.app.Activity
import android.app.FragmentTransaction
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import jkapp.zyronator.discogs.discogs_user_list_items.DisplayDiscogsUserListItemsActivity
import jkapp.zyronator.discogs.discogs_user_lists.update.UpdateDiscogsUserListApiUrlActivity
import jkapp.zyronator.listener.Listener
import jkapp.zyronator.listenermix.*
import jkapp.zyronator.listenermix.find.FindOrCreateListenerMixActivityCallback
import jkapp.zyronator.listenermix.find.FindOrCreateListenerMixApiCall
import jkapp.zyronator.listenermix.get.last_listened.GetLastListenedEarliestApiCall
import jkapp.zyronator.listenermix.get.last_listened.GetLastListenedEarliestApiCallback
import jkapp.zyronator.listenermix.get.last_listened.GetLastListenedLatestApiCall
import jkapp.zyronator.listenermix.get.last_listened.GetLastListenedLatestApiCallback
import jkapp.zyronator.listenermix.update.UpdateLastListenedActivityCallback
import jkapp.zyronator.listenermix.update.UpdateLastListenedApiCall
import jkapp.zyronator.login.LoginActivity
import jkapp.zyronator.mix.find.FindMixesActivity
import jkapp.zyronator.mix.mixapidata.Mix
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

internal class MainActivity : AppCompatActivity(),
        GetLastListenedLatestApiCallback,
        GetLastListenedEarliestApiCallback,
        MixButtonPressed,
        FindOrCreateListenerMixActivityCallback,
        CurrentMixTextPressed,
        UpdateLastListenedActivityCallback,
        ListenerMixDetails,
        GetListenerMixByUrlApiCallback
{
    private val LAST_LISTENED_FRAGMENT = "lastListenedFragment"
    private val LOGIN = 21
    private val MAIN_FRAGMENT_TAG = "mainFragment"
    private val CURRENT_MIX = "currentMix"
    private val NEXT_MIX = "nextMix"
    private val LOGGED_IN_LISTENER = "loggedInListener"

    private val FIND_MIX = 2
    private val UPDATE_DISCOGS_API_URL = 3

    private lateinit var _loggedInListener: Listener
    private lateinit var _currentMix : ListenerMixDisplay
    private lateinit var _nextMix : ListenerMixDisplay

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

            _loggedInListener = BlankListener()
            _currentMix = ListenerMixDisplay("fetching name...", "fetching date...", "", "", "", "")
            _nextMix = ListenerMixDisplay("fetching name...", "fetching date...", "", "", "", "")
        }
        else
        {
            _loggedInListener = savedInstanceState.getParcelable(LOGGED_IN_LISTENER)
            _currentMix = savedInstanceState.getParcelable(CURRENT_MIX)
            _nextMix = savedInstanceState.getParcelable(NEXT_MIX)
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
                    _loggedInListener = data.getParcelableExtra<Listener>(LoginActivity.LISTENER_TAG)

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

                    val apiCall = FindOrCreateListenerMixApiCall(_activity = this, _listenerUrl = _loggedInListener.listenerUrl, _mixUrl = mix._links.self.href)
                    apiCall.execute()
                }
            }
        }
    }

    override fun findOrCreateListenerMixApiResponse(listenerMix: ListenerMixDisplay)
    {
        setNextMix(listenerMix)
    }

    override fun findOrCreateListenerMixApiCallFailed(call: Call<ListenerMix>, t: Throwable)
    {
        Toast.makeText(applicationContext, "CreateListenerMix API call failed: " + t.message, Toast.LENGTH_LONG).show()
    }

    private fun setLastListenedMixes()
    {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)

        val currentMixUrl = sharedPreferences.getString(CURRENT_MIX, "")
        val nextMixUrl = sharedPreferences.getString(NEXT_MIX, "")

        if(currentMixUrl.isNullOrBlank())
        {
            val apiCall = GetLastListenedLatestApiCall(this, _loggedInListener.listenerUrl)
            apiCall.execute()
        }
        else
        {
            val getCurrentListenerMixByUrl = GetListenerMixByUrlApiCall(this, currentMixUrl, CURRENT_MIX)
            getCurrentListenerMixByUrl.execute()
        }

        if(nextMixUrl.isNullOrBlank())
        {
            val apiCall = GetLastListenedEarliestApiCall(this, _loggedInListener.listenerUrl)
            apiCall.execute()
        }
        else
        {
            val getNextListenerMixByUrl = GetListenerMixByUrlApiCall(this, nextMixUrl, NEXT_MIX)
            getNextListenerMixByUrl.execute()
        }
    }

    override fun getLastListenedLatestApiResponse(listenerMix : ListenerMixDisplay)
    {
        _currentMix = listenerMix

        val fragment = fragmentManager.findFragmentByTag(LAST_LISTENED_FRAGMENT) as LastListenedScreenFragment
        fragment.setCurrentMix(_currentMix.mixTitle, _currentMix.lastListenedDate)

        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(CURRENT_MIX, _currentMix.selfUrl)
        editor.apply()
    }

    override fun getLastListenedLatestApiCallFailed(call: Call<ListenerMix>, t: Throwable)
    {
        Toast.makeText(applicationContext, "GetLastListenedLatest API call failed: " + t.message, Toast.LENGTH_LONG).show()
    }

    override fun getLastListenedEarliestApiResponse(listenerMix: ListenerMixDisplay)
    {
        setNextMix(listenerMix)
    }

    private fun setNextMix(listenerMix : ListenerMixDisplay)
    {
        _nextMix = listenerMix

        val fragment = fragmentManager.findFragmentByTag(LAST_LISTENED_FRAGMENT) as LastListenedScreenFragment
        fragment.setNextMix(_nextMix.mixTitle, _nextMix.lastListenedDate)

        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(NEXT_MIX, _nextMix.selfUrl)
        editor.apply()
    }

    override fun getLastListenedEarliestApiCallFailed(call: Call<ListenerMix>, t: Throwable)
    {
        Toast.makeText(applicationContext, "GetLastListenedEarliest API call failed: " + t.message, Toast.LENGTH_LONG).show()
    }

    override fun getListenerMixByUrlApiResponse(listenerMix: ListenerMixDisplay, tag: String)
    {
        if(tag.equals(CURRENT_MIX))
        {
            _currentMix = listenerMix

            val fragment = fragmentManager.findFragmentByTag(LAST_LISTENED_FRAGMENT) as LastListenedScreenFragment
            fragment.setCurrentMix(_currentMix.mixTitle, _currentMix.lastListenedDate)
        }
        else
        {
            _nextMix = listenerMix

            val fragment = fragmentManager.findFragmentByTag(LAST_LISTENED_FRAGMENT) as LastListenedScreenFragment
            fragment.setNextMix(_nextMix.mixTitle, _nextMix.lastListenedDate)
        }
    }

    override fun getListenerMixByUrlApiCallFailed(call: Call<ListenerMix>, t: Throwable, tag: String)
    {
        Toast.makeText(applicationContext, "GetListenerMixByUrl API call failed: " + t.message, Toast.LENGTH_LONG).show()
    }

    override fun getCurrentMixName() : String
    {
        return _currentMix.mixTitle
    }

    override fun getCurrentMixDate() : String
    {
        return _currentMix.lastListenedDate
    }

    override fun getNextMixName() : String
    {
        return _nextMix.mixTitle
    }

    override fun getNextMixDate() : String
    {
        return _nextMix.lastListenedDate
    }

    override fun startNextMixButtonPressed()
    {
        val userAgent = getString(R.string.app_name) + "/" + getString(R.string.version)
        val baseUrl = getString(R.string.base_listener_url)
        val mixUrl = _nextMix.selfUrl

        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val newDate = sdf.format(calendar.time)

        val updateLastListened = UpdateLastListenedApiCall(activityCallBack = this, baseUrl = baseUrl, userAgent = userAgent, listenerMixUrl = mixUrl, newDate = newDate)
        updateLastListened.execute()
    }

    override fun findMixButtonPressed()
    {
        val intent = Intent(this, FindMixesActivity::class.java)
        intent.putExtra(FindMixesActivity.LISTENER_URL, _loggedInListener.listenerUrl)
        startActivityForResult(intent, FIND_MIX)
    }

    override fun currentMixTextPressed()
    {
        if(_currentMix.discogsApiUrl.isNullOrBlank())
        {
            val discogsUserListsIntent = Intent(this, UpdateDiscogsUserListApiUrlActivity::class.java)
            discogsUserListsIntent.putExtra(UpdateDiscogsUserListApiUrlActivity.LISTENER_MIX_DISPLAY, _currentMix)
            startActivityForResult(discogsUserListsIntent, UPDATE_DISCOGS_API_URL)
        }
        else
        {
            displayDiscogsUserListItems()
        }
    }

    private fun displayDiscogsUserListItems()
    {
        val discogsApiUrl = _currentMix.discogsApiUrl

        val listDetailsIntent = Intent(this, DisplayDiscogsUserListItemsActivity::class.java)
        listDetailsIntent.putExtra(DisplayDiscogsUserListItemsActivity.LIST_API_URL_EXTRA, discogsApiUrl)
        startActivity(listDetailsIntent)
    }

    override fun updateLastListenedApiResponse(response: Response<ListenerMix>)
    {
        if(response.raw().code() == 204)
        {
            Toast.makeText(applicationContext, "MixLink listen date changed successfully", Toast.LENGTH_SHORT).show()

            val apiCall = GetLastListenedLatestApiCall(this, _loggedInListener.listenerUrl)
            apiCall.execute()
        }
    }

    override fun updateLastListenedApiCallFailed(call: Call<ListenerMix>, t: Throwable)
    {
        Toast.makeText(applicationContext, "Api Call Failed: " + t.message, Toast.LENGTH_LONG).show()
    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        outState.putParcelable(CURRENT_MIX, _currentMix)
        outState.putParcelable(NEXT_MIX, _nextMix)
        outState.putParcelable(LOGGED_IN_LISTENER, _loggedInListener)

        super.onSaveInstanceState(outState)
    }
}

internal class BlankListener() : Listener, Parcelable {
    override val listenerName: String
    get() = ""

    override val listenerUrl: String
    get() = ""

    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<BlankListener> = object : Parcelable.Creator<BlankListener>
        {
            override fun createFromParcel(source: Parcel): BlankListener = BlankListener(source)
            override fun newArray(size: Int): Array<BlankListener?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int)
    {
    }
}
