package jkapp.zyronator.discogs.discogs_user_lists

import jkapp.zyronator.DiscogsApiAccess
import jkapp.zyronator.discogs.discogs_user_lists.data.DiscogsUserListsApiData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class DiscogsUserListsApiCall(
        private val activity : DiscogsUserListsApiCallback,
        private val userAgent : String,
        private val discogsUserName: String)
{
    internal fun execute()
    {
        val discogsUserListsApiCall = DiscogsApiAccess.apiCalls.getDiscogsUserListsCall(userName = discogsUserName, userAgent = userAgent)
        discogsUserListsApiCall.enqueue(DiscogsUserListCallback(activity))
    }
}

interface DiscogsUserListsApiCallback
{
    fun discogsUserListsApiResponse(call: Call<DiscogsUserListsApiData>, response: Response<DiscogsUserListsApiData>)
    fun discogsUserListsApiCallFailed(call: Call<DiscogsUserListsApiData>, t: Throwable)
}

internal class DiscogsUserListCallback(private val callback : DiscogsUserListsApiCallback) : Callback<DiscogsUserListsApiData>
{
    override fun onResponse(call: Call<DiscogsUserListsApiData>, response: Response<DiscogsUserListsApiData>)
    {
        callback.discogsUserListsApiResponse(call, response)
    }

    override fun onFailure(call: Call<DiscogsUserListsApiData>, t: Throwable)
    {
        callback.discogsUserListsApiCallFailed(call, t)
    }
}

