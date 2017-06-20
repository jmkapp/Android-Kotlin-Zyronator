package jkapp.zyronator.listenermix.create

import jkapp.zyronator.listenermix.find.FindListenerMixApiCall
import jkapp.zyronator.listenermix.find.FindListenerMixApiCallback
import jkapp.zyronator.listenermix.ListenerMix
import retrofit2.Call
import retrofit2.Response

interface CreateAndReturnListenerMixApiCallback
{
    fun createAndReturnListenerMixApiResponse(call: Call<ListenerMix>, response: Response<ListenerMix>)
    fun createAndReturnListenerMixApiCallFailed(call: Call<ListenerMix>, t: Throwable)
}

internal class CreateAndReturnListenerMixApiCall(
        private val _callback : CreateAndReturnListenerMixApiCallback,
        private val _listenerUrl : String,
        private val _mixUrl : String) : CreateListenerMixActivityCallback, FindListenerMixApiCallback
{
    internal fun execute()
    {
        val create = CreateListenerMixApiCall(activityCallback = this, listenerUrl = _listenerUrl, mixUrl = _mixUrl)
        create.execute()
    }

    override fun CreateListenerMixApiResponse(call: Call<Void>, response: Response<Void>)
    {
        val find = FindListenerMixApiCall(_activity = this, _listenerUrl = _listenerUrl, _mixUrl = _mixUrl)
        find.execute()
    }

    override fun CreateListenerMixApiCallFailed(call: Call<Void>, t: Throwable)
    {
        val find = FindListenerMixApiCall(_activity = this, _listenerUrl = _listenerUrl, _mixUrl = _mixUrl)
        find.execute()
    }

    override fun findListenerMixApiResponse(call: Call<ListenerMix>, response: Response<ListenerMix>)
    {
        _callback.createAndReturnListenerMixApiResponse(call, response)
    }

    override fun findListenerMixApiCallFailed(call: Call<ListenerMix>, t: Throwable)
    {
        _callback.createAndReturnListenerMixApiCallFailed(call, t)
    }
}
