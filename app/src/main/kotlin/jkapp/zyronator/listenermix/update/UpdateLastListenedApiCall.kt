package jkapp.zyronator.listenermix.update

import jkapp.zyronator.ApiAccess
import jkapp.zyronator.listenermix.ListenerMix
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class UpdateLastListenedApiCall(
        private val activityCallBack : UpdateLastListenedActivityCallback,
        private val baseUrl : String,
        private val userAgent : String,
        private val listenerMixUrl: String,
        private val newDate : String)
{
    internal fun execute()
    {
        val updates = HashMap<String, String>()
        updates.put("lastListened", newDate)

        val apiCall = ApiAccess.apiCalls.updateLastListened(listenerMixUrl, updates)
        apiCall.enqueue(UpdateLastListenedCallback(activityCallBack))
    }
}

interface UpdateLastListenedActivityCallback
{
    fun updateLastListenedApiResponse(response : Response<ListenerMix>)
    fun updateLastListenedApiCallFailed(call: Call<ListenerMix>, t: Throwable)
}

internal class UpdateLastListenedCallback(private val callback : UpdateLastListenedActivityCallback) : Callback<ListenerMix>
{
    override fun onResponse(call : Call<ListenerMix>, response: Response<ListenerMix>)
    {
        callback.updateLastListenedApiResponse(response)
    }

    override fun onFailure(call: Call<ListenerMix>, t: Throwable)
    {
        callback.updateLastListenedApiCallFailed(call, t)
    }
}

