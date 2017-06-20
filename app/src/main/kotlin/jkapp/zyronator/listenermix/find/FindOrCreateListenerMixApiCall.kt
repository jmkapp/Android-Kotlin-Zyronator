package jkapp.zyronator.listenermix.find

import jkapp.zyronator.listenermix.create.CreateListenerMixActivityCallback
import jkapp.zyronator.listenermix.create.CreateListenerMixApiCall
import jkapp.zyronator.listenermix.ListenerMix
import jkapp.zyronator.listenermix.ListenerMixDisplay
import retrofit2.Call
import retrofit2.Response

interface FindOrCreateListenerMixActivityCallback
{
    fun findOrCreateListenerMixApiResponse(listenerMix : ListenerMixDisplay)
    fun findOrCreateListenerMixApiCallFailed(call: Call<ListenerMix>, t: Throwable)
}

internal class FindOrCreateListenerMixApiCall(
        private val _activity : FindOrCreateListenerMixActivityCallback,
        private val _listenerUrl : String,
        private val _mixUrl : String) : FindListenerMixApiCallback
{
    internal fun execute()
    {
        val find = FindListenerMixApiCall(_activity = this, _listenerUrl = _listenerUrl, _mixUrl = _mixUrl)
        find.execute()
    }

    override fun findListenerMixApiResponse(call: Call<ListenerMix>, response: Response<ListenerMix>)
    {
        if(response.code() == 200)
        {
            val listenerMix = response.body()

            val listenerMixDisplay = ListenerMixDisplay(
                    mixTitle = listenerMix.mixTitle,
                    lastListenedDate = listenerMix.lastListened ?: "",
                    mixUrl = listenerMix._links.mix.href,
                    discogsApiUrl = listenerMix.discogsApiUrl ?: "",
                    discogsWebUrl = listenerMix.discogsWebUrl ?: "",
                    selfUrl = listenerMix._links.self.href
            )

            _activity.findOrCreateListenerMixApiResponse(listenerMixDisplay)
        }
        else
        {
            // not found, so create
            val create = CreateListenerMixCall(_listenerUrl, _mixUrl)
            create.execute()
        }
    }

    override fun findListenerMixApiCallFailed(call: Call<ListenerMix>, t: Throwable)
    {
        _activity.findOrCreateListenerMixApiCallFailed(call, t)
    }

    inner class CreateListenerMixCall(
            private val _listenerUrl: String,
            private val _mixUrl: String) : CreateListenerMixActivityCallback, FindListenerMixApiCallback
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
            // can't return anything directly because because T is Void
            val find = FindListenerMixApiCall(_activity = this, _listenerUrl = _listenerUrl, _mixUrl = _mixUrl)
            find.execute()
        }

        override fun findListenerMixApiResponse(call: Call<ListenerMix>, response: Response<ListenerMix>)
        {
            val listenerMix = response.body()

            val listenerMixDisplay = ListenerMixDisplay(
                    mixTitle = listenerMix.mixTitle,
                    lastListenedDate = listenerMix.lastListened ?: "",
                    mixUrl = listenerMix._links.mix.href,
                    discogsApiUrl = listenerMix.discogsApiUrl ?: "",
                    discogsWebUrl = listenerMix.discogsWebUrl ?: "",
                    selfUrl = listenerMix._links.self.href
            )

            _activity.findOrCreateListenerMixApiResponse(listenerMixDisplay)
        }

        override fun findListenerMixApiCallFailed(call: Call<ListenerMix>, t: Throwable)
        {
            _activity.findOrCreateListenerMixApiCallFailed(call, t)
        }
    }
}