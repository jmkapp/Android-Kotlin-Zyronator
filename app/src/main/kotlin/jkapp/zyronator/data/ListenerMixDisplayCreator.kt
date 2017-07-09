package jkapp.zyronator.data

internal class ListenerMixDisplayCreator(
        listenerMix : ListenerMix)
{
    val listenerMixDisplay : ListenerMixDisplayOld

    init
    {
        listenerMixDisplay = ListenerMixDisplayOld(
                mixTitle = listenerMix.mixTitle,
                lastListenedDate = listenerMix.lastListenedDate ?: "",
                mixUrl = listenerMix._links.mix.href,
                discogsApiUrl = listenerMix.discogsApiUrl ?: "",
                discogsWebUrl = listenerMix.discogsWebUrl ?: "",
                comment = listenerMix.comment ?: "",
                selfUrl = listenerMix._links.self.href)
    }
}
