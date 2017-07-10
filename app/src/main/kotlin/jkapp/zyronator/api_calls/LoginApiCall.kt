package jkapp.zyronator.api_calls

import jkapp.zyronator.data.ListenerDisplay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



interface LoginApiCallback
{
    fun LoginApiResponse(call: Call<ListenerDisplay>, response: Response<ListenerDisplay>)
    fun LoginApiCallFailed(call: Call<ListenerDisplay>, t: Throwable)
}

internal class LoginApiCall(
        private val _callback : LoginApiCallback,
        private val _listenerName : String,
        private val _password : String)
{
    internal fun execute()
    {
        ApiAccess.login(_listenerName, _password)

        val apiCall = ApiAccess.apiCalls.findListenerByName(_listenerName)
        apiCall.enqueue(FindListenerApiCallCallback())
    }

    inner class FindListenerApiCallCallback() : Callback<ListenerDisplay>
    {
        override fun onResponse(call: Call<ListenerDisplay>, response: Response<ListenerDisplay>)
        {
            _callback.LoginApiResponse(call, response)
        }

        override fun onFailure(call: Call<ListenerDisplay>, t: Throwable)
        {
            _callback.LoginApiCallFailed(call, t)
        }
    }
}


