package jkapp.zyronator.listenermix.update

import jkapp.zyronator.ApiAccess
import jkapp.zyronator.mix.mixapidata.Mix
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class UpdateMixApiCall(
        private val _activity : UpdateMixApiCallCallback,
        private val _listenerMixUrl: String,
        private val _discogsApiUrl : String)
{
    fun execute()
    {
        val updates = HashMap<String, String>()
        updates.put("discogsApiUrl", _discogsApiUrl)

        val apiCall = ApiAccess.apiCalls.getMix(_listenerMixUrl)
        apiCall.enqueue(GetMixCallback(_activity, updates))
    }
}

interface UpdateMixApiCallCallback
{
    fun updateMixApiResponse(call: Call<Mix>, response: Response<Mix>)
    fun updateMixApiCallFailed(call: Call<Mix>, t: Throwable)
}

internal class GetMixCallback(
        private val _callback : UpdateMixApiCallCallback,
        private val _updates : Map<String, String>): Callback<Mix>
{
    override fun onResponse(call: Call<Mix>, response: Response<Mix>)
    {
        val apiCall = ApiAccess.apiCalls.updateDiscogsApiUrl(response.body()._links.self.href, _updates)
        apiCall.enqueue(UpdateMixCallback(_callback))
    }

    override fun onFailure(call: Call<Mix>, t: Throwable)
    {
        _callback.updateMixApiCallFailed(call, t)
    }
}

internal class UpdateMixCallback(
        private val _callback : UpdateMixApiCallCallback) : Callback<Mix>
{
    override fun onResponse(call: Call<Mix>, response: Response<Mix>)
    {
        _callback.updateMixApiResponse(call, response)
    }

    override fun onFailure(call: Call<Mix>, t: Throwable)
    {
        _callback.updateMixApiCallFailed(call, t)
    }
}