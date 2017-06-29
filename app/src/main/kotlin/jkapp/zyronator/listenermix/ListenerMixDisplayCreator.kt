package jkapp.zyronator.listenermix

internal class ListenerMixDisplayCreator(
        listenerMix : ListenerMix)
{
    val listenerMixDisplay : ListenerMixDisplay

    init
    {
        listenerMixDisplay = ListenerMixDisplay(
                mixTitle = listenerMix.mixTitle,
                lastListenedDate =  listenerMix.lastListenedDate ?: "",
                mixUrl =  listenerMix._links.mix.href,
                discogsApiUrl =  listenerMix.discogsApiUrl ?: "",
                discogsWebUrl =  listenerMix.discogsWebUrl ?: "",
                comment =  listenerMix.comment ?: "",
                selfUrl = listenerMix._links.self.href)
    }
}
