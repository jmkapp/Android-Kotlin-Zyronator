package jkapp.zyronator.listenermix.get.last_listened

import jkapp.zyronator.ApiAccess
import jkapp.zyronator.listenermix.ListenerMix
import jkapp.zyronator.listenermix.ListenerMixDisplay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface GetLastListenedLatestApiCallback
{
    fun getLastListenedLatestApiResponse(listenerMix : ListenerMixDisplay)
    fun getLastListenedLatestApiCallFailed(call: Call<ListenerMix>, t: Throwable)
}

internal class GetLastListenedLatestApiCall(
        private val _callBack : GetLastListenedLatestApiCallback,
        private val _listenerUrl: String)
{
    internal fun execute()
    {
        val apiCall = ApiAccess.apiCalls.findLatestListenerMix(_listenerUrl)
        apiCall.enqueue(GetLastListenedLatestApiCallResult(_callBack))
    }
}

internal class GetLastListenedLatestApiCallResult(private val _callBack: GetLastListenedLatestApiCallback) : Callback<ListenerMix>
{
    override fun onResponse(call: Call<ListenerMix>, response: Response<ListenerMix>)
    {
        if(response.isSuccessful)
        {
            val listenerMix = response.body()
            _callBack.getLastListenedLatestApiResponse(ListenerMixDisplay(
                    listenerMix.mixTitle,
                    listenerMix.lastListenedDate ?: "",
                    listenerMix._links.self.href,
                    listenerMix.discogsApiUrl ?: "",
                    listenerMix.discogsWebUrl ?: "",
                    listenerMix.comment ?: "",
                    listenerMix._links.self.href))
        }
        else
        {
            _callBack.getLastListenedLatestApiResponse(ListenerMixDisplay("", "", "", "", "", "", ""))
        }
    }

    override fun onFailure(call: Call<ListenerMix>, t: Throwable)
    {
        _callBack.getLastListenedLatestApiCallFailed(call, t)
    }
}
