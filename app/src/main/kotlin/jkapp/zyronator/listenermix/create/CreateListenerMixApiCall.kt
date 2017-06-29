package jkapp.zyronator.listenermix.create

import jkapp.zyronator.Api
import jkapp.zyronator.ApiAccess
import jkapp.zyronator.listenermix.ListenerMix
import jkapp.zyronator.listenermix.ListenerMixDisplay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface CreateListenerMixActivityCallback
{
    fun CreateListenerMixApiResponse(listenerMix : ListenerMixDisplay)
    fun CreateListenerMixApiCallFailed(call : Call<ListenerMix>, t : Throwable)
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

    private class CreateListenerMixApiCallback(private val callback : CreateListenerMixActivityCallback) : Callback<ListenerMix>
    {
        override fun onResponse(call: Call<ListenerMix>, response: Response<ListenerMix>)
        {
            if(response.isSuccessful)
            {
                val listenerMix = response.body()

                val listenerMixDisplay = ListenerMixDisplay(
                        mixTitle = listenerMix.mixTitle,
                        lastListenedDate = listenerMix.lastListenedDate ?: "",
                        mixUrl = listenerMix._links.mix.href,
                        discogsApiUrl = listenerMix.discogsApiUrl ?: "",
                        discogsWebUrl = listenerMix.discogsWebUrl ?: "",
                        comment = listenerMix.comment ?: "",
                        selfUrl = listenerMix._links.self.href)

                callback.CreateListenerMixApiResponse(listenerMixDisplay)
            }
        }

        override fun onFailure(call: Call<ListenerMix>, t: Throwable)
        {
            callback.CreateListenerMixApiCallFailed(call, t)
        }
    }
}



