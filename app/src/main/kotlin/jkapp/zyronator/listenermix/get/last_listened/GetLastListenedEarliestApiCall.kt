package jkapp.zyronator.listenermix.get.last_listened

import jkapp.zyronator.ApiAccess
import jkapp.zyronator.listenermix.ListenerMix
import jkapp.zyronator.listenermix.ListenerMixDisplay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface GetLastListenedEarliestApiCallback
{
    fun getLastListenedEarliestApiResponse(listenerMix : ListenerMixDisplay)
    fun getLastListenedEarliestApiCallFailed(call: Call<ListenerMix>, t: Throwable)
}

internal class GetLastListenedEarliestApiCall(
        private val _callBack : GetLastListenedEarliestApiCallback,
        private val _listenerUrl: String)
{
    internal fun execute()
    {
        val apiCall = ApiAccess.apiCalls.findEarliestListenerMix(_listenerUrl)
        apiCall.enqueue(GetLastListenedEarliestApiCallResult(_callBack))
    }
}

internal class GetLastListenedEarliestApiCallResult(private val _callBack: GetLastListenedEarliestApiCallback) : Callback<ListenerMix>
{
    override fun onResponse(call: Call<ListenerMix>, response: Response<ListenerMix>)
    {
        if(response.isSuccessful)
        {
            val listenerMix = response.body()
            _callBack.getLastListenedEarliestApiResponse(ListenerMixDisplay(
                    listenerMix.mixTitle,
                    listenerMix.lastListened ?: "",
                    listenerMix._links.self.href,
                    listenerMix.discogsApiUrl ?: "",
                    listenerMix.discogsWebUrl ?: "",
                    listenerMix._links.self.href))
        }
        else
        {
            _callBack.getLastListenedEarliestApiResponse(ListenerMixDisplay("", "", "", "", "", ""))
        }
    }

    override fun onFailure(call: Call<ListenerMix>, t: Throwable)
    {
        _callBack.getLastListenedEarliestApiCallFailed(call, t)
    }
}
