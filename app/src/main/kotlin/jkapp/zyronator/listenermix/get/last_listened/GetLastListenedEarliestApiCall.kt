package jkapp.zyronator.listenermix.get.last_listened

import jkapp.zyronator.ApiAccess
import jkapp.zyronator.listenermix.ListenerMix
import jkapp.zyronator.listenermix.ListenerMixDisplay
import jkapp.zyronator.listenermix.ListenerMixDisplayCreator
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
            val listenerMixDisplay = ListenerMixDisplayCreator(response.body()).listenerMixDisplay
            _callBack.getLastListenedEarliestApiResponse(listenerMixDisplay)
        }
        else
        {
            _callBack.getLastListenedEarliestApiResponse(ListenerMixDisplay("", "", "", "", "", "", ""))
        }
    }

    override fun onFailure(call: Call<ListenerMix>, t: Throwable)
    {
        _callBack.getLastListenedEarliestApiCallFailed(call, t)
    }
}
