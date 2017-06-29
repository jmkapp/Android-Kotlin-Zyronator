package jkapp.zyronator.listenermix.update

import jkapp.zyronator.ApiAccess
import jkapp.zyronator.listenermix.ListenerMix
import jkapp.zyronator.listenermix.ListenerMixDisplay
import jkapp.zyronator.listenermix.ListenerMixDisplayCreator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class UpdateLastListenedApiCall(
        private val _callBack: UpdateLastListenedActivityCallback,
        private val _listenerMixUrl: String,
        private val _newComment : String,
        private val _newDate: String)
{
    internal fun execute()
    {
        val updates = HashMap<String, String>()
        updates.put("lastListenedDate", _newDate)
        updates.put("comment", _newComment)

        val apiCall = ApiAccess.apiCalls.updateLastListened(_listenerMixUrl, updates)
        apiCall.enqueue(UpdateLastListenedCallback(_callBack))
    }
}

interface UpdateLastListenedActivityCallback
{
    fun updateLastListenedApiResponse(listenerMix : ListenerMixDisplay)
    fun updateLastListenedApiCallFailed(call: Call<ListenerMix>, t: Throwable)
}

internal class UpdateLastListenedCallback(private val callback : UpdateLastListenedActivityCallback) : Callback<ListenerMix>
{
    override fun onResponse(call : Call<ListenerMix>, response: Response<ListenerMix>)
    {
        if(response.isSuccessful)
        {
            val listenerMixDisplay = ListenerMixDisplayCreator(response.body()).listenerMixDisplay
            callback.updateLastListenedApiResponse(listenerMixDisplay)
        }
    }

    override fun onFailure(call: Call<ListenerMix>, t: Throwable)
    {
        callback.updateLastListenedApiCallFailed(call, t)
    }
}

