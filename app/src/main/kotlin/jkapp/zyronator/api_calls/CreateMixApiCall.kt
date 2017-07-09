package jkapp.zyronator.api_calls

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class CreateMixApiCall(
        private val activityCallBack : CreateMixActivityCallback,
        private val mixTitle: String,
        private val discogsApiUrl : String,
        private val discogsWebUrl : String)
{
    internal fun execute()
    {
        val map = HashMap<String, String>()
        map.put("title", mixTitle)
        map.put("discogsApiUrl", discogsApiUrl)
        map.put("discogsWebUrl", discogsWebUrl)

        val apiCall = ApiAccess.apiCalls.createMix(map)
        apiCall.enqueue(CreateMixApiCallback(activityCallBack))
    }
}

interface CreateMixActivityCallback
{
    fun createMixApiResponse(call: Call<Void>, response : Response<Void>)
    fun createMixApiCallFailed(call : Call<Void>, t : Throwable)
}

internal class CreateMixApiCallback(private val callback : CreateMixActivityCallback) : Callback<Void>
{
    override fun onResponse(call: Call<Void>, response: Response<Void>)
    {
        callback.createMixApiResponse(call, response)
    }

    override fun onFailure(call: Call<Void>, t: Throwable)
    {
        callback.createMixApiCallFailed(call, t)
    }
}



