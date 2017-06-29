package jkapp.zyronator.main_activity

import android.content.Context
import android.util.Log
import jkapp.zyronator.MainActivity
import jkapp.zyronator.listenermix.LastListenedScreenFragment
import jkapp.zyronator.mix.LastListenedMixes
import jkapp.zyronator.mix.LastListenedMixesApiCall
import jkapp.zyronator.mix.LastListenedMixesApiCallback
import jkapp.zyronator.mix.LastListenedMixesDisplay
import retrofit2.Call
import retrofit2.Response

internal class LastListenedMixesGetter(
        private val _activity : MainActivity,
        private val _listenerUrl : String) : LastListenedMixesApiCallback
{
    internal fun execute()
    {
        val apiCall = LastListenedMixesApiCall(this, _listenerUrl = _listenerUrl)
        apiCall.execute()
    }

    override fun lastListenedMixesApiCallResponse(lastListened: LastListenedMixesDisplay)
    {
        _activity.lastListenedMixes = lastListened

        val fragment = _activity.fragmentManager.findFragmentByTag(_activity.LAST_LISTENED_FRAGMENT) as LastListenedScreenFragment
        fragment.setCurrentMix(_activity.lastListenedMixes.currentMixTitle, _activity.lastListenedMixes.currentMixLastListenedDate)
        fragment.setNextMix(_activity.lastListenedMixes.nextMixTitle, _activity.lastListenedMixes.nextMixLastListenedDate)

//        val sharedPreferences = _activity.getPreferences(Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.putString(_activity.CURRENT_LISTENER_MIX, _activity.lastListenedMixes.currentMixSelfUrl)
//        editor.putString(_activity.NEXT_LISTENER_MIX, _activity.lastListenedMixes.nextMixSelfUrl)
//        editor.apply()

    }

    override fun lastListenerMixesApiCallFailed(call: Call<LastListenedMixes>, t: Throwable)
    {
        Log.i("Test", "Test")
    }
}
