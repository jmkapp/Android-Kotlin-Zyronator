package jkapp.zyronator.activities

import android.content.Context
import android.widget.Toast
import jkapp.zyronator.fragments.LastListenedScreenFragment
import jkapp.zyronator.api_calls.LastListenedMixesApiCall
import jkapp.zyronator.api_calls.LastListenedMixesApiCallback
import jkapp.zyronator.data.LastListenedMixes
import retrofit2.Call
import retrofit2.Response

internal class LastListenedMixesGetter(
        private val _activity : MainActivity,
        private val _lastListenedUrl : String) : LastListenedMixesApiCallback
{
    internal fun execute()
    {
        val apiCall = LastListenedMixesApiCall(this, _lastListenedUrl = _lastListenedUrl)
        apiCall.execute()
    }

    override fun lastListenedMixesApiCallResponse(call: Call<LastListenedMixes>, response: Response<LastListenedMixes>)
    {
        val lastListenedMixes = response.body()
        _activity.currentListenerMix = lastListenedMixes.currentListenerMix
        _activity.nextListenerMix = lastListenedMixes.nextListenerMix

        val fragment = _activity.fragmentManager.findFragmentByTag(_activity.LAST_LISTENED_FRAGMENT) as LastListenedScreenFragment
        fragment.setCurrentMix(_activity.currentListenerMix.mixTitle, _activity.currentListenerMix.lastListenedDate)
        fragment.setNextMix(_activity.nextListenerMix.mixTitle, _activity.nextListenerMix.lastListenedDate)

        val sharedPreferences = _activity.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(_activity.CURRENT_LISTENER_MIX, _activity.currentListenerMix._links?.self?.href ?: "")
        editor.putString(_activity.NEXT_LISTENER_MIX, _activity.nextListenerMix._links?.self?.href ?: "")
        editor.apply()
    }

    override fun lastListenerMixesApiCallFailed(call: Call<LastListenedMixes>, t: Throwable)
    {
        Toast.makeText(_activity.applicationContext, "LastListenedMixes API call failed: " + t.message, Toast.LENGTH_LONG).show()
    }
}
