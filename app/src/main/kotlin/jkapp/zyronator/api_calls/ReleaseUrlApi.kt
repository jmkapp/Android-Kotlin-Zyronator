package jkapp.zyronator.api_calls

import jkapp.zyronator.api_calls.discogs.DiscogsApiAccess
import jkapp.zyronator.data.discogs.ListItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class ReleaseUrlApi(
        private val _userAgent : String,
        private val _releaseId : String,
        private val _callback : ReleaseUrlApiCallback)
{
    internal fun execute()
    {
        val apiCall = DiscogsApiAccess.apiCalls.getReleaseWebUrl(userAgent = _userAgent, releaseId = _releaseId)
        apiCall.enqueue(ReleaseUrlApiCallbackResponse(_callback))
    }
}

data class ListDetailsApiResponse(
        val items : java.util.List<ListItem>)

data class ReleaseWebUrl(
        val uri : String)

interface ReleaseUrlApiCallback
{
    fun webUrlApiResponse(call: Call<ReleaseWebUrl>, response: Response<ReleaseWebUrl>)
    fun webUrlApiCallFailure(call: Call<ReleaseWebUrl>, t:Throwable)
}

internal class ReleaseUrlApiCallbackResponse(private val _callback : ReleaseUrlApiCallback) : Callback<ReleaseWebUrl>
{
    override fun onResponse(call: Call<ReleaseWebUrl>, response: Response<ReleaseWebUrl>)
    {
        _callback.webUrlApiResponse(call, response)
    }

    override fun onFailure(call: Call<ReleaseWebUrl>, t: Throwable)
    {
        _callback.webUrlApiCallFailure(call, t)
    }
}