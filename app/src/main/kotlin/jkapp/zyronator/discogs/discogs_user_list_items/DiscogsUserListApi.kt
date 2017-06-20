package jkapp.zyronator.discogs.discogs_user_list_items

import jkapp.zyronator.DiscogsApiAccess
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Url

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

//interface ListDetailsApiCall
//{
//    @GET("/lists/{listid}")
//    fun getListDetailsCall(
//            @Path("listid") listId : String,
//            @Header("user-agent") userAgent : String) : Call<ListDetailsApiResponse>
//
//    @GET
//    fun getListDetailsByListUrl(
//            @Url listUrl : String,
//            @Header("user-agent") userAgent : String) : Call<ListDetailsApiResponse>
//}

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