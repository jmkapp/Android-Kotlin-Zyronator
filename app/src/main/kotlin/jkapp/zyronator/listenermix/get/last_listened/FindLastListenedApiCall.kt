package jkapp.zyronator.listenermix.get.last_listened

import jkapp.zyronator.ApiAccess
import jkapp.zyronator.listenermix.ListenerMix
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface FindLastListenedCallBack
{
    fun lastListenedLatestMixApiResponse(response : Response<ListenerMix>)
    fun lastListenedLatestApiCallFailed(call: Call<ListenerMix>, t: Throwable)
    fun lastListenedEarliestMixApiResponse(response : Response<ListenerMix>)
    fun lastListenedEarliestMixApiCallFailed(call: Call<ListenerMix>, t: Throwable)
}

internal class FindLastListenedApiCall(
        private val activityCallBack : FindLastListenedCallBack,
        private val listenerUrl: String)
{
    internal fun execute()
    {
        val call1 = FindLastListenedEarliestApiCall(activityCallBack, listenerUrl)
        call1.execute()
        val call2 = FindLastListenedLatestApiCall(activityCallBack, listenerUrl)
        call2.execute()
    }
}

internal class FindLastListenedEarliestApiCall(
        private val activityCallBack : FindLastListenedCallBack,
        private val listenerUrl: String)
{
    internal fun execute()
    {
        val apiCall2 = ApiAccess.apiCalls.findEarliestListenerMix(listenerUrl)
        apiCall2.enqueue(LastListenedEarliestMixCallback(activityCallBack))
    }

}

internal class FindLastListenedLatestApiCall(
        private val activityCallBack : FindLastListenedCallBack,
        private val listenerUrl: String)
{
    internal fun execute()
    {
        val apiCall1 = ApiAccess.apiCalls.findLatestListenerMix(listenerUrl)
        apiCall1.enqueue(LastListenedLatestMixCallback(activityCallBack))
    }
}

internal class LastListenedLatestMixCallback(private val callback : FindLastListenedCallBack) : Callback<ListenerMix>
{
    override fun onResponse(call: Call<ListenerMix>, response: Response<ListenerMix>)
    {
        callback.lastListenedLatestMixApiResponse(response)
    }

    override fun onFailure(call: Call<ListenerMix>, t: Throwable)
    {
        callback.lastListenedLatestApiCallFailed(call, t)
    }
}

internal class LastListenedEarliestMixCallback(private val callback : FindLastListenedCallBack) : Callback<ListenerMix>
{
    override fun onResponse(call: Call<ListenerMix>, response: Response<ListenerMix>)
    {
        callback.lastListenedEarliestMixApiResponse(response)
    }

    override fun onFailure(call: Call<ListenerMix>, t: Throwable)
    {
        callback.lastListenedEarliestMixApiCallFailed(call, t)
    }
}




