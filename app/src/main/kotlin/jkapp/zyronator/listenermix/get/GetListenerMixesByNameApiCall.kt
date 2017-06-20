package jkapp.zyronator.listenermix.get

import jkapp.zyronator.ApiAccess
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class GetListenerMixesByNameApiCall(
        private val _activityCallBack: ListenerMixesActivityApiCallBack,
        private val _listenerName: String)
{
    internal fun execute()
    {
        val apiCall = ApiAccess.apiCalls.findMixesByListenerName(_listenerName)
        apiCall.enqueue(GetListenerMixesApiCallback(_activityCallBack))
    }
}

interface ListenerMixesActivityApiCallBack
{
    fun apiResponse(response : Response<ListenerMixesApiData>)
    fun apiCallFailed(call: Call<ListenerMixesApiData>, t: Throwable)
}

internal class GetListenerMixesApiCallback(private val callback : ListenerMixesActivityApiCallBack) : Callback<ListenerMixesApiData>
{
    override fun onResponse(call: Call<ListenerMixesApiData>, response: Response<ListenerMixesApiData>)
    {
        callback.apiResponse(response)
    }

    override fun onFailure(call: Call<ListenerMixesApiData>, t: Throwable)
    {
        callback.apiCallFailed(call, t)
    }
}

