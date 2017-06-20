package jkapp.zyronator.listenermix.create

import jkapp.zyronator.Api
import jkapp.zyronator.ApiAccess
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface CreateListenerMixActivityCallback
{
    fun CreateListenerMixApiResponse(call: Call<Void>, response: Response<Void>)
    fun CreateListenerMixApiCallFailed(call : Call<Void>, t : Throwable)
}

internal class CreateListenerMixApiCall(
        val activityCallback: CreateListenerMixActivityCallback,
        private val listenerUrl : String,
        private val mixUrl : String)
{
    internal fun execute()
    {
        val map = HashMap<String, String>()
        map.put(Api.LISTENER, listenerUrl)
        map.put(Api.MIX, mixUrl)

        val apiCall = ApiAccess.apiCalls.createListenerMix(values = map)
        apiCall.enqueue(CreateListenerMixApiCallback(activityCallback))
    }

    private class CreateListenerMixApiCallback(private val callback : CreateListenerMixActivityCallback) : Callback<Void>
    {
        override fun onResponse(call: Call<Void>, response: Response<Void>)
        {
            callback.CreateListenerMixApiResponse(call, response)
        }

        override fun onFailure(call: Call<Void>, t: Throwable)
        {
            callback.CreateListenerMixApiCallFailed(call, t)
        }
    }
}



