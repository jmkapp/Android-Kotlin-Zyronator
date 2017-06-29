package jkapp.zyronator.main_activity

import android.content.Context
import android.widget.Toast
import jkapp.zyronator.MainActivity
import jkapp.zyronator.listener.Listener
import jkapp.zyronator.listenermix.LastListenedScreenFragment
import jkapp.zyronator.listenermix.ListenerMix
import jkapp.zyronator.listenermix.ListenerMixDisplay
import jkapp.zyronator.listenermix.get.last_listened.GetLastListenedEarliestApiCall
import jkapp.zyronator.listenermix.get.last_listened.GetLastListenedEarliestApiCallback
import retrofit2.Call

internal class NextMixSetter(
        private val _activity : MainActivity,
        private val _loggedInListener : Listener,
        private val _currentListenerMixUrl : String) : GetLastListenedEarliestApiCallback

{
    internal fun execute()
    {
        val apiCall = GetLastListenedEarliestApiCall(this, _loggedInListener.listenerUrl)
        apiCall.execute()
    }

    internal fun execute(nextListenerMixUrl : ListenerMixDisplay)
    {
        setNextMix(nextListenerMixUrl)
    }

    private fun setNextMix(listenerMix : ListenerMixDisplay)
    {
        val fragment = _activity.fragmentManager.findFragmentByTag(_activity.LAST_LISTENED_FRAGMENT) as LastListenedScreenFragment

        val sharedPreferences = _activity.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        if(listenerMix.selfUrl == _currentListenerMixUrl)
        {
            editor.remove(_activity.NEXT_LISTENER_MIX)
            editor.apply()

            fragment.setNextMix(title = "", date= "")
        }
        else
        {
            _activity.nextListenerMix = listenerMix

            fragment.setNextMix(_activity.nextListenerMix.mixTitle, _activity.nextListenerMix.lastListenedDate)

            editor.putString(_activity.NEXT_LISTENER_MIX, _activity.nextListenerMix.selfUrl)
            editor.apply()
        }

        editor.apply()
    }

    override fun getLastListenedEarliestApiResponse(listenerMix: ListenerMixDisplay)
    {
        setNextMix(listenerMix)
    }

    override fun getLastListenedEarliestApiCallFailed(call: Call<ListenerMix>, t: Throwable)
    {
        Toast.makeText(_activity.applicationContext, "GetLastListenedEarliest API call failed: " + t.message, Toast.LENGTH_LONG).show()
    }
}