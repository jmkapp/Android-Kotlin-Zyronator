package jkapp.zyronator.api_calls

import jkapp.zyronator.data.ListenerMixDisplay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface CreateListenerMixActivityCallback
{
    fun CreateListenerMixApiResponse(call: Call<ListenerMixDisplay>, response: Response<ListenerMixDisplay>)
    fun CreateListenerMixApiCallFailed(call : Call<ListenerMixDisplay>, t : Throwable)
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

    private class CreateListenerMixApiCallback(private val callback : CreateListenerMixActivityCallback) : Callback<ListenerMixDisplay>
    {
        override fun onResponse(call: Call<ListenerMixDisplay>, response: Response<ListenerMixDisplay>)
        {
            callback.CreateListenerMixApiResponse(call, response)
        }

        override fun onFailure(call: Call<ListenerMixDisplay>, t: Throwable)
        {
            callback.CreateListenerMixApiCallFailed(call, t)
        }
    }
}



