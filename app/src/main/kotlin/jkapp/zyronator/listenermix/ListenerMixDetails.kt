package jkapp.zyronator.listenermix

interface ListenerMixDetails
{
    fun getCurrentMixName() : String

    fun getCurrentMixDate() : String

    fun getNextMixName() : String

    fun getNextMixDate() : String
}