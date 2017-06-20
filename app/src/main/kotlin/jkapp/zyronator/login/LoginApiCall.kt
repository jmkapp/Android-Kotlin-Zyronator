package jkapp.zyronator.login

import jkapp.zyronator.ApiAccess
import jkapp.zyronator.listener.get.embedded.ListenerApiData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



interface LoginApiCallback
{
    fun LoginApiResponse(call: Call<ListenerApiData>, response: Response<ListenerApiData>)
    fun LoginApiCallFailed(call: Call<ListenerApiData>, t: Throwable)
}

internal class LoginApiCall(
        private val _callback : LoginApiCallback,
        private val _listenerName : String,
        private val _password : String)
{
    internal fun execute()
    {
        val apiAccess = ApiAccess
        ApiAccess.login(_listenerName, _password)

        val apiCall = ApiAccess.apiCalls.findListenerByName(_listenerName)
        apiCall.enqueue(FindListenerApiCallCallback())
    }

    inner class FindListenerApiCallCallback() : Callback<ListenerApiData>
    {
        override fun onResponse(call: Call<ListenerApiData>, response: Response<ListenerApiData>)
        {
            _callback.LoginApiResponse(call, response)
        }

        override fun onFailure(call: Call<ListenerApiData>, t: Throwable)
        {
            _callback.LoginApiCallFailed(call, t)
        }
    }
}


