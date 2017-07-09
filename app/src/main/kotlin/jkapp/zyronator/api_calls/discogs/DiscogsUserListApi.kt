package jkapp.zyronator.api_calls.discogs

import jkapp.zyronator.api_calls.ListDetailsApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class DiscogsUserListApi(
        private val _userAgent : String,
        private val _listApiUrl : String,
        private val _callback : DiscogsUserListApiCallback)
{
    internal fun execute()
    {
        val listDetailsApiCall = DiscogsApiAccess.apiCalls.getUserList(listUrl = _listApiUrl, userAgent = _userAgent)
        listDetailsApiCall.enqueue(DiscogsUserListApiCallbackResponse(_callback))
    }
}

interface DiscogsUserListApiCallback
{
    fun discogsUserListApiCallResponse(call : Call<ListDetailsApiResponse>, response : Response<ListDetailsApiResponse>)
    fun discogsUserListApiCallFailed(call : Call<ListDetailsApiResponse>, t : Throwable)
}

internal class DiscogsUserListApiCallbackResponse(private val _callback: DiscogsUserListApiCallback) : Callback<ListDetailsApiResponse>
{
    override fun onResponse(call: Call<ListDetailsApiResponse>, response: Response<ListDetailsApiResponse>)
    {
        _callback.discogsUserListApiCallResponse(call, response)
    }

    override fun onFailure(call: Call<ListDetailsApiResponse>, t: Throwable)
    {
        _callback.discogsUserListApiCallFailed(call, t)
    }
}