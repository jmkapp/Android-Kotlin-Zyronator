package jkapp.zyronator.mix

data class LastListenedMixes(
        val currentListenerMix: LastListenedMix,
        val nextListenerMix: LastListenedMix,
        val _links : LastListenedLinks?)

data class LastListenedMix(
        val mixTitle : String,
        val recordedDate : String = "",
        val comment : String = "",
        val discogsApiUrl : String = "",
        val discogsWebUrl: String = "",
        val lastListenedDate : String = "",
        val _links : LastListenedMixLinks?)

data class LastListenedLinks(
        val self : LastListenedMixesLink)

data class LastListenedMixLinks(
        val self : LastListenedMixesLink,
        val listenerMix : LastListenedMixesLink,
        val listener : LastListenedMixesLink,
        val mix : LastListenedMixesLink)

data class LastListenedMixesLink(
        val href : String)