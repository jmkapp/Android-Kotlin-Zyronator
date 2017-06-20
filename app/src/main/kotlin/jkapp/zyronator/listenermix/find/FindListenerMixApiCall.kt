package jkapp.zyronator.listenermix.find

import jkapp.zyronator.ApiAccess
import jkapp.zyronator.listenermix.ListenerMix
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface FindListenerMixApiCallback
{
    fun findListenerMixApiResponse(call: Call<ListenerMix>, response: Response<ListenerMix>)
    fun findListenerMixApiCallFailed(call: Call<ListenerMix>, t: Throwable)
}

internal class FindListenerMixApiCall(
        private val _activity: FindListenerMixApiCallback,
        private val _listenerUrl : String,
        private val _mixUrl : String)
{
    internal fun execute()
    {
        val apiCall = ApiAccess.apiCalls.findListenerMix(_mixUrl, _listenerUrl)
        apiCall.enqueue(FindListenerMixCallback(_activity))
    }

    private class FindListenerMixCallback(private val _activity : FindListenerMixApiCallback) : Callback<ListenerMix>
    {
        override fun onResponse(call: Call<ListenerMix>, response: Response<ListenerMix>)
        {
            _activity.findListenerMixApiResponse(call, response)
        }

        override fun onFailure(call: Call<ListenerMix>, t: Throwable)
        {
            _activity.findListenerMixApiCallFailed(call, t)
        }
    }
}



