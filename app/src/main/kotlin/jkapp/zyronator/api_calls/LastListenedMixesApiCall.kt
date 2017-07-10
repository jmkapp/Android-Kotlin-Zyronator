package jkapp.zyronator.api_calls

import jkapp.zyronator.data.LastListenedMixes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface LastListenedMixesApiCallback
{
    fun lastListenedMixesApiCallResponse(call: Call<LastListenedMixes>, response: Response<LastListenedMixes>)
    fun lastListenerMixesApiCallFailed(call: Call<LastListenedMixes>, t: Throwable)
}

internal class LastListenedMixesApiCall(
        private val _callback : LastListenedMixesApiCallback,
        private val _lastListenedUrl : String)
{
    internal fun execute()
    {
        val apiCall = ApiAccess.apiCalls.lastListened(_lastListenedUrl)
        apiCall.enqueue(LastListenedApiCallback(_callback))
    }
}

internal class LastListenedApiCallback(private val _callback : LastListenedMixesApiCallback) : Callback<LastListenedMixes>
{
    override fun onResponse(call: Call<LastListenedMixes>, response: Response<LastListenedMixes>)
    {
        if(response.isSuccessful)
        {
            _callback.lastListenedMixesApiCallResponse(call, response)
        }
    }

    override fun onFailure(call: Call<LastListenedMixes>, t: Throwable)
    {
        _callback.lastListenerMixesApiCallFailed(call, t)
    }
}
