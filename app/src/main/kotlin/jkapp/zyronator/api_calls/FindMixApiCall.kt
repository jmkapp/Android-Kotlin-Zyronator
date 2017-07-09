package jkapp.zyronator.api_calls

import jkapp.zyronator.data.Mix
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class FindMixApiCall(
        private val _activity : FindMixActivityCallback,
        private val _mixTitle : String,
        private val _discogsApiUrl : String,
        private val _discogsWebUrl : String)
{
    internal fun execute()
    {
        val apiCall = ApiAccess.apiCalls.findMix(_mixTitle, _discogsApiUrl, _discogsWebUrl)
        apiCall.enqueue(FindMixApiCallback(_activity))
    }
}

interface FindMixActivityCallback
{
    fun findMixApiResponse(call: Call<Mix>, response: Response<Mix>)
    fun findMixApiCallFailed(call: Call<Mix>, t: Throwable)
}

internal class FindMixApiCallback(private val _activityCallback : FindMixActivityCallback) : Callback<Mix>
{
    override fun onResponse(call: Call<Mix>, response: Response<Mix>)
    {
        _activityCallback.findMixApiResponse(call, response)
    }

    override fun onFailure(call: Call<Mix>, t: Throwable)
    {
        _activityCallback.findMixApiCallFailed(call, t)
    }

}

