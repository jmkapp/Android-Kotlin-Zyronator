package jkapp.zyronator.mix

import jkapp.zyronator.ApiAccess
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface LastListenedMixesApiCallback
{
    fun lastListenedMixesApiCallResponse(lastListened : LastListenedMixesDisplay)
    fun lastListenerMixesApiCallFailed(call: Call<LastListenedMixes>, t: Throwable)
}

internal class LastListenedMixesApiCall(
        private val _callback : LastListenedMixesApiCallback,
        private val _listenerUrl : String)
{
    internal fun execute()
    {
        val apiCall = ApiAccess.apiCalls.lastListened(_listenerUrl + "/lastListened")
        apiCall.enqueue(LastListenedApiCallback(_callback))
    }
}

internal class LastListenedApiCallback(private val _callback : LastListenedMixesApiCallback) : Callback<LastListenedMixes>
{
    override fun onResponse(call: Call<LastListenedMixes>, response: Response<LastListenedMixes>)
    {
        if(response.isSuccessful)
        {
            val lastListenedMixes = response.body()

            val lastListenedDisplay = LastListenedMixesDisplay(
                    currentMixTitle = lastListenedMixes.currentListenerMix.mixTitle,
                    currentMixRecorded = lastListenedMixes.currentListenerMix.recordedDate,
                    currentMixComment = lastListenedMixes.currentListenerMix.comment,
                    currentMixDiscogsApiUrl = lastListenedMixes.currentListenerMix.discogsApiUrl,
                    currentMixDiscogsWebUrl = lastListenedMixes.currentListenerMix.discogsWebUrl,
                    currentMixLastListenedDate = lastListenedMixes.currentListenerMix.lastListenedDate,
                    currentListenerMixSelf = lastListenedMixes.currentListenerMix._links?.listenerMix?.href ?: "",
                    currentMixSelfUrl = lastListenedMixes.currentListenerMix._links?.self?.href ?: "",
                    nextMixTitle = lastListenedMixes.nextListenerMix.mixTitle,
                    nextMixRecorded = lastListenedMixes.nextListenerMix.recordedDate,
                    nextMixComment = lastListenedMixes.nextListenerMix.comment,
                    nextMixDiscogsApiUrl = lastListenedMixes.nextListenerMix.discogsApiUrl,
                    nextMixDiscogsWebUrl = lastListenedMixes.nextListenerMix.discogsWebUrl,
                    nextMixLastListenedDate = lastListenedMixes.nextListenerMix.lastListenedDate,
                    nextListenerMixSelf = lastListenedMixes.nextListenerMix._links?.listenerMix?.href ?: "",
                    nextMixSelfUrl = lastListenedMixes.nextListenerMix._links?.self?.href ?: ""
            )

            _callback.lastListenedMixesApiCallResponse(lastListenedDisplay)
        }
    }

    override fun onFailure(call: Call<LastListenedMixes>, t: Throwable)
    {
        _callback.lastListenerMixesApiCallFailed(call, t)
    }
}
