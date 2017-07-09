package jkapp.zyronator.api_calls

import jkapp.zyronator.data.LastListenedMixes
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
    fun updateLastListenedApiResponse(call : Call<LastListenedMixes>, response: Response<LastListenedMixes>)
    fun updateLastListenedApiCallFailed(call: Call<LastListenedMixes>, t: Throwable)
}

internal class UpdateLastListenedCallback(private val _callback : UpdateLastListenedActivityCallback) : Callback<LastListenedMixes>
{
    override fun onResponse(call : Call<LastListenedMixes>, response: Response<LastListenedMixes>)
    {
        if(response.isSuccessful)
        {
            _callback.updateLastListenedApiResponse(call, response)
        }
    }

    override fun onFailure(call: Call<LastListenedMixes>, t: Throwable)
    {
        _callback.updateLastListenedApiCallFailed(call, t)
    }
}

