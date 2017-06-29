package jkapp.zyronator.main_activity

import android.content.Context
import android.widget.Toast
import jkapp.zyronator.MainActivity
import jkapp.zyronator.listenermix.*
import jkapp.zyronator.listenermix.update.UpdateLastListenedActivityCallback
import jkapp.zyronator.listenermix.update.UpdateLastListenedApiCall
import retrofit2.Call
import java.text.SimpleDateFormat
import java.util.*

internal class CurrentMixUpdater(
        private val _activity : MainActivity,
        private val _currentListenerMixUrl: String,
        private val _comment : String) :
        UpdateLastListenedActivityCallback
{
    private val _newDate : String

    init
    {
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        _newDate = sdf.format(calendar.time)
    }

    internal fun update()
    {
        if(_currentListenerMixUrl.isBlank())
            return

        val sharedPreferences = _activity.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(_activity.CURRENT_LISTENER_MIX)
        editor.apply()

        val updateLastListened = UpdateLastListenedApiCall(_callBack = this, _listenerMixUrl = _currentListenerMixUrl, _newComment = _comment, _newDate = _newDate)
        updateLastListened.execute()
    }

    override fun updateLastListenedApiResponse(listenerMix: ListenerMixDisplay)
    {
        _activity.currentListenerMix = listenerMix

        val fragment = _activity.fragmentManager.findFragmentByTag(_activity.LAST_LISTENED_FRAGMENT) as LastListenedScreenFragment
        fragment.setCurrentMix(_activity.currentListenerMix.mixTitle, _activity.currentListenerMix.lastListenedDate)

        val sharedPreferences = _activity.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(_activity.CURRENT_LISTENER_MIX, _activity.currentListenerMix.selfUrl)
        editor.apply()
    }

    override fun updateLastListenedApiCallFailed(call: Call<ListenerMix>, t: Throwable)
    {
        Toast.makeText(_activity.applicationContext, "UpdateLastListened API call failed: " + t.message, Toast.LENGTH_LONG).show()
    }
}
