package jkapp.zyronator.listenermix

import jkapp.zyronator.ApiAccess
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface GetListenerMixByUrlApiCallback
{
    fun getListenerMixByUrlApiResponse(listenerMix : ListenerMixDisplay, tag : String)
    fun getListenerMixByUrlApiCallFailed(call: Call<ListenerMix>, t: Throwable, tag : String)
}

internal class GetListenerMixByUrlApiCall(
        private val _callback: GetListenerMixByUrlApiCallback,
        private val _listenerMixUrl : String,
        private val _tag : String)
{
    internal fun execute()
    {
        val apiCall = ApiAccess.apiCalls.getListenerMix(_listenerMixUrl)
        apiCall.enqueue(GetListenerMixByUrlApiResult(_callback))
    }

    inner class GetListenerMixByUrlApiResult(private val _callback : GetListenerMixByUrlApiCallback) : Callback<ListenerMix>
    {
        override fun onResponse(call: Call<ListenerMix>, response: Response<ListenerMix>)
        {
            if(response.isSuccessful)
            {
                val listenerMix = response.body()
                val listenerMixDisplay = ListenerMixDisplay(
                        listenerMix.mixTitle,
                        listenerMix.lastListenedDate ?: "",
                        listenerMix._links.self.href,
                        listenerMix.discogsApiUrl ?: "",
                        listenerMix.discogsWebUrl ?: "",
                        listenerMix.comment ?: "",
                        listenerMix._links.self.href)
                _callback.getListenerMixByUrlApiResponse(listenerMixDisplay, _tag)
            }
            else
            {
                val listenerMixDisplay = ListenerMixDisplay("", "", "", "", "", "", "")
                _callback.getListenerMixByUrlApiResponse(listenerMixDisplay, _tag)
            }
        }

        override fun onFailure(call: Call<ListenerMix>, t: Throwable)
        {
            _callback.getListenerMixByUrlApiCallFailed(call, t, _tag)
        }
    }
}




