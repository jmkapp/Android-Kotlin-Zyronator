package jkapp.zyronator

import android.app.Activity
import android.app.FragmentTransaction
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.Toast
import jkapp.zyronator.discogs.discogs_user_list_items.DisplayDiscogsUserListItemsActivity
import jkapp.zyronator.discogs.discogs_user_lists.update.UpdateDiscogsUserListApiUrlActivity
import jkapp.zyronator.listener.Listener
import jkapp.zyronator.listenermix.*
import jkapp.zyronator.listenermix.create.CreateListenerMixActivityCallback
import jkapp.zyronator.listenermix.create.CreateListenerMixApiCall
import jkapp.zyronator.login.LoginActivity
import jkapp.zyronator.main_activity.*
import jkapp.zyronator.mix.LastListenedMixesDisplay
import jkapp.zyronator.mix.find.FindMixesActivity
import jkapp.zyronator.mix.mixapidata.Mix
import retrofit2.Call

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
    private val UPDATE_DISCOGS_API_URL = 3

    private lateinit var _loggedInListener: Listener
    internal lateinit var currentListenerMix: ListenerMixDisplay
    internal lateinit var nextListenerMix: ListenerMixDisplay
    internal lateinit var lastListenedMixes : LastListenedMixesDisplay

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
            currentListenerMix = ListenerMixDisplay("fetching name...", "fetching date...", "", "", "", "", "")
            nextListenerMix = ListenerMixDisplay("fetching name...", "fetching date...", "", "", "", "", "")
            lastListenedMixes = LastListenedMixesDisplay()
        }
        else
        {
            _loggedInListener = savedInstanceState.getParcelable(LOGGED_IN_LISTENER)
            // lastListenedMixes
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
                    val apiCall = CreateListenerMixApiCall(activityCallback = this, listenerUrl = _loggedInListener.listenerUrl, mixUrl = mix._links.self.href)
                    apiCall.execute()
                }
            }
        }
    }

    override fun CreateListenerMixApiResponse(listenerMix : ListenerMixDisplay)
    {
        setNextMix(listenerMix)
    }

    override fun CreateListenerMixApiCallFailed(call: Call<ListenerMix>, t: Throwable)
    {
        Toast.makeText(applicationContext, "CreateListenerMix API call failed: " + t.message, Toast.LENGTH_LONG).show()
    }

    private fun setLastListenedMixes()
    {
        val lastListenedMixesGetter = LastListenedMixesGetter(_activity = this, _listenerUrl = _loggedInListener.listenerUrl)
        lastListenedMixesGetter.execute()
    }

    private fun setNextMix(listenerMix : ListenerMixDisplay)
    {
        nextListenerMix = listenerMix

        val fragment = fragmentManager.findFragmentByTag(LAST_LISTENED_FRAGMENT) as LastListenedScreenFragment
        fragment.setNextMix(nextListenerMix.mixTitle, nextListenerMix.lastListenedDate)

        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(NEXT_LISTENER_MIX, nextListenerMix.selfUrl)
        editor.apply()
    }

    override fun getListenerMixByUrlApiResponse(listenerMix: ListenerMixDisplay, tag: String)
    {
        if(tag.equals(CURRENT_LISTENER_MIX))
        {
            currentListenerMix = listenerMix

            val fragment = fragmentManager.findFragmentByTag(LAST_LISTENED_FRAGMENT) as LastListenedScreenFragment
            fragment.setCurrentMix(currentListenerMix.mixTitle, currentListenerMix.lastListenedDate)
        }
        else
        {
            nextListenerMix = listenerMix

            val fragment = fragmentManager.findFragmentByTag(LAST_LISTENED_FRAGMENT) as LastListenedScreenFragment
            fragment.setNextMix(nextListenerMix.mixTitle, nextListenerMix.lastListenedDate)
        }
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
        val nextListenerMixUrl = lastListenedMixes.nextListenerMixSelf

        val currentMixSetter = CurrentMixUpdater(_activity = this, _currentListenerMixUrl = nextListenerMixUrl, _comment = nextListenerMix.comment)
        currentMixSetter.update()

        val nextMixSetter = NextMixSetter(_activity = this, _loggedInListener = _loggedInListener, _currentListenerMixUrl = nextListenerMixUrl)
        nextMixSetter.execute()
    }

    override fun findMixButtonPressed()
    {
        val intent = Intent(this, FindMixesActivity::class.java)
        intent.putExtra(FindMixesActivity.LISTENER_URL, _loggedInListener.listenerUrl)
        startActivityForResult(intent, FIND_MIX)
    }

    override fun currentMixTextPressed()
    {
        if(currentListenerMix.discogsApiUrl.isNullOrBlank())
        {
            val discogsUserListsIntent = Intent(this, UpdateDiscogsUserListApiUrlActivity::class.java)
            discogsUserListsIntent.putExtra(UpdateDiscogsUserListApiUrlActivity.LISTENER_MIX_DISPLAY, currentListenerMix)
            startActivityForResult(discogsUserListsIntent, UPDATE_DISCOGS_API_URL)
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
//        outState.putParcelable(CURRENT_LISTENER_MIX, currentListenerMix)
//        outState.putParcelable(NEXT_LISTENER_MIX, nextListenerMix)
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
