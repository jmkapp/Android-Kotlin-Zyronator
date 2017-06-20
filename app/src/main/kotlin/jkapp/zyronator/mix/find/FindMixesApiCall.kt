package jkapp.zyronator.mix.find

import jkapp.zyronator.ApiAccess
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class FindMixesApiCall(
        private val activityCallBack : FindMixesActivityCallback,
        private val searchText: String)
{
    internal fun execute()
    {
        val apiCall = ApiAccess.apiCalls.findMixes(searchText)
        apiCall.enqueue(FindMixesApiCallback(activityCallBack))
    }
}

interface FindMixesActivityCallback
{
    fun findMixesApiResponse(response : Response<MixApiCallResultData>)
    fun findMixesApiCallFailed(call : Call<MixApiCallResultData>, t : Throwable)
}

internal class FindMixesApiCallback(private val callback : FindMixesActivityCallback) : Callback<MixApiCallResultData>
{
    override fun onResponse(call: Call<MixApiCallResultData>, response: Response<MixApiCallResultData>)
    {
        callback.findMixesApiResponse(response)
    }

    override fun onFailure(call: Call<MixApiCallResultData>, t: Throwable)
    {
        callback.findMixesApiCallFailed(call, t)
    }

}
